package com.example.cookiekai.controller;

import com.example.cookiekai.entity.Users;
import com.example.cookiekai.service.UsersService;
import com.example.cookiekai.service.impl.RolesServiceImpl;
import com.example.cookiekai.service.impl.UsersServiceImpl;
import com.example.cookiekai.util.FileUploadUtil;
import com.example.cookiekai.util.UserExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UsersServiceImpl usersService;
    @Autowired
    private RolesServiceImpl rolesService;

    @GetMapping("/dashboard/users")
    public String viewNhanVien(Model model) {
        return listPage(0, "id", "asc", null, model);
    }

    @GetMapping("/dashboard/users/page/{pageNo}")
    public String listPage(@PathVariable("pageNo") Integer pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortType") String sortType, @RequestParam("keyword") String keyWord, Model model) {
        Page<Users> page = usersService.pageUser(pageNo, sortField, sortType, keyWord);
        String revertType = sortType.equals("asc") ? "desc" : "asc";
        model.addAttribute("user", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortRevert", revertType);
        model.addAttribute("keyWord", keyWord);
        return "dashboard/nhan-vien/trang-chu-nhan-vien";
    }

    @PostMapping("/dashboard/users/save")
    public String saveNhanVien(@Valid @ModelAttribute("user") Users users, BindingResult result, Model model, @RequestParam("image") MultipartFile file, HttpSession session) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("roleList", rolesService.listRoles());
            session.setAttribute("message", "Save user unsuccessfully");
            return "dashboard/nhan-vien/view-add-nhan-vien";
        } else if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            users.setPhotos(fileName);
            Users savedUser = usersService.addOrUpdateUser(users);
            String uploadDirectory = "user-photos/" + savedUser.getId();
            FileUploadUtil.saveFile(uploadDirectory, fileName, file);
        }
        session.setAttribute("message", "Save user successfully");
        return "redirect:/dashboard/users";
    }

    @GetMapping("/dashboard/users/new")
    public String viewAddNhanVien(Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("roleList", rolesService.listRoles());
        return "dashboard/nhan-vien/view-add-nhan-vien";
    }

    @GetMapping("/dashboard/users/detail/{id}")
    public String detailNhanVien(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", usersService.getOne(id));
        model.addAttribute("roleList", rolesService.listRoles());
        return "dashboard/nhan-vien/view-update-nhan-vien";
    }

    @PostMapping("/dashboard/users/update/{id}")
    public String updateNhanVien(@Valid @ModelAttribute("user") Users users, BindingResult result, Model model, @RequestParam("image") MultipartFile file, HttpSession session) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("roleList", rolesService.listRoles());
            session.setAttribute("message", "Save user unsuccessfully");
            return "dashboard/nhan-vien/view-add-nhan-vien";
        } else if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            users.setPhotos(fileName);
            Users savedUser = usersService.addOrUpdateUser(users);
            String uploadDirectory = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDirectory(uploadDirectory);
            FileUploadUtil.saveFile(uploadDirectory, fileName, file);
        } else {
            usersService.addOrUpdateUser(users);
        }
        session.setAttribute("message", "Save user successfully");
        return "redirect:/dashboard/users";
    }

    @GetMapping("/dashboard/users/delete/{id}")
    public String deleteNhanVien(@PathVariable("id") Integer id) throws IOException {
        Users usersDelete = usersService.getOne(id);
        String uploadDirectory = "user-photos/" + usersDelete.getId();
        FileUploadUtil.cleanDirectory(uploadDirectory);
        usersService.deleteUser(id);
        return "redirect:/dashboard/users";
    }

    @GetMapping("/dashboard/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Users> list = usersService.listUser();
        list.forEach(s -> System.out.println(s.getId()));
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.exportExcel(list, response);
    }

    @GetMapping("/dashboard/users/{id}/enabled/{status}")
    public String updateEnableStatus(@PathVariable("id") String id, @PathVariable("status") Boolean status) {
        Integer ids = Integer.parseInt(id);
        usersService.updateEnableUser(status, ids);
        return "redirect:/dashboard/users";
    }
}
