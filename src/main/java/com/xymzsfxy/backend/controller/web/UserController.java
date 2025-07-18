package com.xymzsfxy.backend.controller.web;

import com.xymzsfxy.backend.dto.LoginDTO;
import com.xymzsfxy.backend.dto.UserInfoDTO;
import com.xymzsfxy.backend.entity.Users;
import com.xymzsfxy.backend.returncode.Result;
import com.xymzsfxy.backend.service.UserService;
import com.xymzsfxy.backend.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/web/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired(required = false)
    private JavaMailSender sender;

    @PostMapping("/register")
    public Result<?> register(@RequestParam @Pattern(regexp = "^\\S{4,16}$") String username,
                              @RequestParam String passwordHash) {
        Users u = userService.findByUserRegisterName(username);
        if (u == null) {
            userService.register(username, passwordHash);
            return Result.success();
        } else {
            return Result.badRequest("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<LoginDTO> login(
            @Pattern(regexp = "^\\S{4,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String passwordHash,
            HttpServletResponse response
    ) {
        Users u = userService.findByUserRegisterName(username);
        if (u == null) {
            return Result.notFound("该用户");
        }

        boolean isEqPassword = userService.passwordEq(passwordHash, username);
        Users byUserLoginName = userService.getByUserLoginName(username);

        if (!isEqPassword) {
            return Result.badRequest("密码错误");
        }

        // 生成JWT令牌并设置到Cookie
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", u.getId());
        claims.put("username", u.getUsername());

        String accessToken = jwtUtils.generateAccessToken(claims);
        String refreshToken = jwtUtils.generateRefreshToken(claims);

        // 设置HttpOnly Cookie
        response.addHeader("Set-Cookie", jwtUtils.createAccessTokenCookie(accessToken).toString());
        response.addHeader("Set-Cookie", jwtUtils.createRefreshTokenCookie(refreshToken).toString());

        LoginDTO loginResponse = LoginDTO.fromEntity(byUserLoginName);

        return Result.success(loginResponse);
    }

    // 登出接口
    @PostMapping("/logout")
    public Result<?> logout(HttpServletResponse response) {
        // 清除Cookie中的令牌
        response.addHeader("Set-Cookie", jwtUtils.createLogoutCookie("access_token").toString());
        response.addHeader("Set-Cookie", jwtUtils.createLogoutCookie("refresh_token").toString());
        return Result.success();
    }

    @GetMapping("/get")
    public Result<Result.PageData<List<Users>>> getAllUserInfo(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<Users> allUserInfo = userService.getAllUserInfo(page, size);
        Long infoCount = userService.getInfoCount();
        return Result.successWithPage(infoCount, allUserInfo);
    }

    @GetMapping("/getName")
    public Result<Result.PageData<List<Users>>> getName(
            @ModelAttribute Users user,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        List<Users> byUserName = userService.findByUserName(user.getUsername(), page, size);
        Long nameCount = userService.getNameCount(user.getUsername());
        return Result.successWithPage(nameCount, byUserName);
    }

    // 获取当前登录用户信息
    @GetMapping("/info")
    public Result<UserInfoDTO> getCurrentUser(
            @CookieValue(name = "access_token", required = false) String accessToken) {
        UserInfoDTO currentUser = userService.getCurrentUser(accessToken);

        if (currentUser == null) {
            return Result.badRequest("未登录或令牌无效");
        }

        return Result.success(currentUser);
    }

    // 更新用户信息
    @PutMapping("/update")
    public Result updateUserInfo(
            @ModelAttribute UserInfoDTO userInfoDTO,
            @CookieValue(name = "access_token", required = false) String accessToken) {
        boolean updatedUser = userService.updateUserInfo(accessToken,userInfoDTO);
        if (updatedUser) {
            return Result.badRequest("未登录或令牌无效");
        }else {
            return Result.success();
        }
    }


    // 更新用户头像

    // QQ邮箱发送验证码
    @PostMapping("/mail")
    public Result sentMail(String mail, HttpServletRequest request){

        try{
            // 生成 6 位数字验证码
//            Random random = new Random();
//            String code = random.nextInt(8999) + 1000+"";// 验证码
//            // 当前时间
//            LocalDateTime currentTime = LocalDateTime.now();
//
//            //2min有效时间
//            LocalDateTime expireTime = currentTime.plusMinutes(2);
//
//            //存储到session
//            request.getSession().setAttribute("expireTime", expireTime);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("车辆故障预警"); // 发送邮件的标题
            message.setText("【车辆故障预警】⚠️\n" +
                    "车型：大众迈腾\n" +
                    "故障部件：发动机冷却系统\n" +
                    "危险等级：★★★☆☆\n" +
                    "预估影响：动力输出下降/高温风险\n" +
                    "建议处理时限：立即\n\n" +
                    "安全提示：\n" +
                    "① 立即靠边停车，开启双闪\n" +
                    "② 关闭空调系统降低发动机负载\n" +
                    "③ 检查水温表，避免发动机高温\n" +
                    "④ 联系专业救援服务：400-820-1888");// 发送邮件的内容
            message.setTo(mail); // 指定要接收邮件的用户邮箱账号
            message.setFrom("3170195273@qq.com");

            sender.send(message); // 调用send方法发送邮件即可

            //先用的session可以采用security
//            request.getSession().setAttribute("qq",mail);
//            request.getSession().setAttribute("code",code);
//            request.getSession().setAttribute("expireTime",expireTime);
//            request.getSession().setMaxInactiveInterval(60*2);
            return Result.success();
        }
        catch (Exception e){
            System.out.println(e);
            return Result.badRequest("发送失败");
        }

    }


}