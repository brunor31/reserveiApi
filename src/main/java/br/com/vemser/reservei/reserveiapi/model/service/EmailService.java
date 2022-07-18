package br.com.vemser.reservei.reserveiapi.model.service;

import br.com.vemser.reservei.reserveiapi.model.dto.ClienteDTO;
import br.com.vemser.reservei.reserveiapi.model.dto.ReservaDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;

    public String getTemplateCreate(ClienteDTO clienteDTO, ReservaDTO reservaDTO) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", clienteDTO.getNome());
        dados.put("email", clienteDTO.getEmail());
        dados.put("dataEntrada", reservaDTO.getDataEntrada());
        dados.put("dataSaida", reservaDTO.getDataSaida());
        dados.put("valorReserva", reservaDTO.getValorReserva());
        dados.put("from", from);
        Template template = fmConfiguration.getTemplate("email-templateCreate.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
    public void sendMail(ClienteDTO clienteDTO, ReservaDTO reservaDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//            ClassPathResource image = new ClassPathResource("src/main/resources/templates/images/img1.png");
            FileSystemResource image1 = new FileSystemResource(new File("C:\\Users\\bruno\\Desktop\\reservei-api\\src\\main\\resources\\templates\\img1.png"));
            FileSystemResource image2 = new FileSystemResource(new File("C:\\Users\\bruno\\Desktop\\reservei-api\\src\\main\\resources\\templates\\img2.png"));
            FileSystemResource image3 = new FileSystemResource(new File("C:\\Users\\bruno\\Desktop\\reservei-api\\src\\main\\resources\\templates\\Reservei.png"));

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(clienteDTO.getEmail());
            mimeMessageHelper.addInline("image1.png", image1);
            mimeMessageHelper.addInline("image2.png", image2);
            mimeMessageHelper.addInline("image3.png", image3);
            mimeMessageHelper.addInline("image3.png", image3);
            mimeMessageHelper.setText(getTemplateCreate(clienteDTO, reservaDTO), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
