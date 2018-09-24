package com.alysson.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.alysson.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine; 	

	@Autowired
	private JavaMailSender javaMailSender; 

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage smm = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(smm);
	} 

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {		
		SimpleMailMessage smm = new SimpleMailMessage();		
		smm.setTo(pedido.getCliente().getEmail());
		smm.setFrom(sender);		
		smm.setSubject("Pedido confirmado!");
		smm.setSentDate(new Date(System.currentTimeMillis()));		
		smm.setText(pedido.toString());		
		return smm;
	}
	
	protected String htmlFromTemplatePedido(Pedido pedido) {		
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return templateEngine.process("email/confirmacaoPedido", context);
	}	

	@Override		
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(mm);	
		} 
		catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}	
	}
	
	private MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);		
		helper.setTo(pedido.getCliente().getEmail());
		helper.setFrom(sender);
		helper.setSubject("Pedido confirmado!");
		helper.setSentDate(new Date(System.currentTimeMillis()));
		helper.setText(htmlFromTemplatePedido(pedido), true);		
		return mimeMessage;
	}
}