package com.nuevafrecuencia.patria5kweb.controller;

import com.nuevafrecuencia.patria5kweb.model.Participant;
import com.nuevafrecuencia.patria5kweb.service.ParticipantService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private final ParticipantService service;

    @Value("${app.admin-password:2026}")
    private String adminPassword;

    public AdminController(ParticipantService service) {
        this.service = service;
    }

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String password, HttpSession session, Model model) {
        if (adminPassword.equals(password)) {
            session.setAttribute("ADMIN_AUTH", true);
            return "redirect:/admin";
        }
        model.addAttribute("error", "Contraseña incorrecta");
        return "admin-login";
    }

    @PostMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String dashboard(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("participants", service.list(q));
        model.addAttribute("total", service.count());
        model.addAttribute("q", q == null ? "" : q);
        return "admin";
    }

    @GetMapping("/admin/participantes/{id}/editar")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("participant", service.get(id));
        return "admin-edit";
    }

    @PostMapping("/admin/participantes/{id}/editar")
    public String update(@PathVariable Long id,
                         @Valid Participant participant,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            participant.setId(id);
            participant.setBibNumber(service.get(id).getBibNumber());
            model.addAttribute("participant", participant);
            return "admin-edit";
        }
        service.update(id, participant);
        return "redirect:/admin";
    }

    @PostMapping("/admin/participantes/{id}/eliminar")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/exportar.csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=participantes_corriendo_por_mi_patria_5k.csv");
        response.getOutputStream().write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
        var writer = response.getWriter();
        writer.println("Numero,Nombre,Edad,Municipio,Telefono,Fecha de inscripcion");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Participant> participants = service.list(null);
        for (Participant p : participants) {
            writer.printf("%s,%s,%d,%s,%s,%s%n",
                    csv(p.getBibNumber()), csv(p.getName()), p.getAge(),
                    csv(p.getMunicipality()), csv(p.getPhone()), csv(p.getCreatedAt().format(fmt)));
        }
        writer.flush();
    }

    private String csv(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}
