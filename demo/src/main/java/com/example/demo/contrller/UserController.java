package com.example.demo.contrller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.common.Result;
import com.example.demo.exception.ServiceException;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.UpPw;
import com.example.demo.service.UserService;
import com.example.demo.util.AiAllText;
import com.example.demo.util.TokenUtils;
import com.github.pagehelper.PageInfo;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.demo.util.AiAllText.sendText;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")

public class UserController {

@Autowired
private UserService userService;
    @PostMapping("/inputfingerprint")
    public Result inputfingerprint(@RequestBody Object data) {
        System.out.println(data);
        return null;
    }
//登录
    @PostMapping("/login")
    public Result test(@RequestBody AppUser user) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, ApiException {
        HashMap<String, Object> userdata = new HashMap<>();
        System.out.println(user);
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("密码账号为空");
        }
        AppUser user1 = userService.login(user);
        user1.setPassword("");
        String phone = user1.getPhone();
        List<String> sendtextuser = new ArrayList<>();
        sendtextuser.add(phone);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:mm");
        String formattedNow = now.format(formatter);
        sendText("您已在"+formattedNow+"登录系统:http://110.40.182.197/login",sendtextuser);
        //sendText("",sendtextuser);

        return Result.success(user1);
    }
    @GetMapping("/info/{a}")
    public HashMap<String, Object> info(@PathVariable("a") String a){
        System.out.println("a"+a);
        HashMap<String, Object> userdata = new HashMap<>();
userdata.put("code","200");
userdata.put("age","20");
userdata.put("sex","男");
userdata.put("username","zhang");
        return userdata;
    }
    //修改密码
    @PutMapping("/uppw")
    public Result update(@RequestBody UpPw user){
        AppUser currentUser = TokenUtils.getCurrentUser();
        if (user.getPassword().equals(user.getConfirmPassword())) {
            return Result.error("修改的密码与原密码不能一致");
        }else if(user.getConfirmPassword().equals(currentUser.getPassword())){
            return Result.error("原密码输入错误");
        }else {
            userService.userService(user);
        }
        return Result.success();
    }
    //修改用户信息
    @PutMapping("/upuser")
    public Result upuser(@RequestBody AppUser user){
        System.out.println(user);
        userService.upuser(user);
        return Result.success();
    }

    //usersupplier
    @GetMapping("/productinformation")
    //查询商品信息
    private PageInfo<AppUser> productinformation(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String username,
            @RequestParam String name

    ){
        PageInfo<AppUser> user= userService.selectuser(pageNum,pageSize,username,name);
        return user;
    }
    @PostMapping("/add")
    public Result add(@RequestBody AppUser user) {
        String username = user.getUsername();
        LambdaQueryWrapper<AppUser> wrapper = new LambdaQueryWrapper<>();
       wrapper.eq(AppUser::getUsername, username);
        AppUser one = userService.getOne(wrapper);
        if (one != null) {
            return Result.error("用户名已经存在");
        }else {
            user.setPassword("1");
            userService.save(user);
            return Result.success("添加用户成功");
        }
    }
    @PutMapping("/update")
    public Result update(@RequestBody AppUser user){
        String username = user.getUsername();
        LambdaUpdateWrapper<AppUser> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(AppUser::getName,user.getName())
                .set(AppUser::getUsername,user.getUsername())
                .set(AppUser::getRole,user.getRole())
                .set(AppUser::getPassword,"1")
                .eq(AppUser::getId,user.getId());
                if(user.getPhone()!=null){
                    lambdaUpdateWrappera.set(AppUser::getPhone,user.getPhone())
                    .eq(AppUser::getId,user.getId());
                }
                if(user.getEmail()!=null){
                    lambdaUpdateWrappera.set(AppUser::getEmail,user.getEmail())
                    .eq(AppUser::getId,user.getId());
                }

        AppUser newInfo = new AppUser();
        userService.update(newInfo,lambdaUpdateWrappera);
        System.out.println(user);
        return Result.success("更新成功");
    }
    @DeleteMapping("/delete/{id}")

    public Result delete(@PathVariable Integer id){
        LambdaQueryWrapper<AppUser> wrappera = new LambdaQueryWrapper<>();
        wrappera.eq(AppUser::getId, id);
        AppUser one = userService.getOne(wrappera);



        AppUser currentUser = TokenUtils.getCurrentUser();
        if (id.equals(currentUser.getId())) {
            throw new ServiceException("不能删除当前的用户");
        }
        if(one.getUsername().equals("admin")){
            throw new ServiceException("不能删除admin管理员");
        }
        LambdaQueryWrapper<AppUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppUser::getId, id);
        userService.remove(wrapper);
        return Result.success("删除成功");
    }
}
