/*   1:    */ package com.asinfo.as2.jms;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.MensajeEmail;
/*   4:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail;
/*   5:    */ import com.asinfo.as2.servicio.ServicioEnvioEmail.DatosSMTP;
/*   6:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   7:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   8:    */ import java.io.ByteArrayInputStream;
/*   9:    */ import java.io.File;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Date;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import java.util.Properties;
/*  18:    */ import java.util.concurrent.ConcurrentMap;
/*  19:    */ import javax.activation.DataHandler;
/*  20:    */ import javax.activation.DataSource;
/*  21:    */ import javax.activation.FileDataSource;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.MessageDriven;
/*  24:    */ import javax.ejb.TransactionAttribute;
/*  25:    */ import javax.ejb.TransactionAttributeType;
/*  26:    */ import javax.jms.MapMessage;
/*  27:    */ import javax.jms.Message;
/*  28:    */ import javax.jms.MessageListener;
/*  29:    */ import javax.mail.Authenticator;
/*  30:    */ import javax.mail.BodyPart;
/*  31:    */ import javax.mail.Message.RecipientType;
/*  32:    */ import javax.mail.MessagingException;
/*  33:    */ import javax.mail.Multipart;
/*  34:    */ import javax.mail.PasswordAuthentication;
/*  35:    */ import javax.mail.Session;
/*  36:    */ import javax.mail.Transport;
/*  37:    */ import javax.mail.internet.InternetAddress;
/*  38:    */ import javax.mail.internet.MimeBodyPart;
/*  39:    */ import javax.mail.internet.MimeMessage;
/*  40:    */ import javax.mail.internet.MimeMultipart;
/*  41:    */ import javax.mail.util.ByteArrayDataSource;
/*  42:    */ 
/*  43:    */ @MessageDriven(mappedName="java:/jms/queue/AS2MailQueue", activationConfig={@javax.ejb.ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge"), @javax.ejb.ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"), @javax.ejb.ActivationConfigProperty(propertyName="destination", propertyValue="java:/jms/queue/AS2MailQueue")})
/*  44:    */ public class MDBEnvioMail
/*  45:    */   implements MessageListener
/*  46:    */ {
/*  47:    */   @EJB
/*  48:    */   private ServicioGenerico<MensajeEmail> servicioMensajeEmail;
/*  49:    */   
/*  50:    */   @TransactionAttribute(TransactionAttributeType.NEVER)
/*  51:    */   public void onMessage(Message message)
/*  52:    */   {
/*  53: 63 */     MapMessage mapMessage = (MapMessage)message;
/*  54:    */     try
/*  55:    */     {
/*  56: 65 */       int idMapMessage = mapMessage.getInt("idMapMessage");
/*  57: 66 */       MensajeEmail me = (MensajeEmail)ServicioEnvioEmail.cmMensajeEmail.get(Integer.valueOf(idMapMessage));
/*  58: 68 */       if (me != null)
/*  59:    */       {
/*  60: 70 */         int idOrganizaion = me.getIdOrganizacion();
/*  61: 71 */         String para = me.getPara();
/*  62: 72 */         String tituloMensaje = me.getTituloMensaje();
/*  63: 73 */         String mensajeCuerpo = me.getCuerpoMensaje();
/*  64: 74 */         String[] archivos = FuncionesUtiles.stringToArray(me.getArchivosAdjuntos());
/*  65: 75 */         String[] mapaCid = FuncionesUtiles.stringToArray(me.getMapaCid());
/*  66: 76 */         byte[] adjuntoByte = me.getAdjuntoByte();
/*  67: 77 */         String nombreAdjuntoByte = me.getNombreAdjuntoByte();
/*  68: 78 */         String tipoAdjuntoByte = me.getTipoAdjuntoByte();
/*  69:    */         
/*  70: 80 */         List<MimeBodyPart> listaAdjuntos = new ArrayList();
/*  71: 81 */         if (adjuntoByte != null)
/*  72:    */         {
/*  73: 82 */           InputStream isPdf = new ByteArrayInputStream(adjuntoByte);
/*  74: 83 */           DataSource source = new ByteArrayDataSource(isPdf, tipoAdjuntoByte);
/*  75: 84 */           MimeBodyPart attachmentPart = new MimeBodyPart();
/*  76: 85 */           attachmentPart.setDataHandler(new DataHandler(source));
/*  77: 86 */           attachmentPart.setFileName(nombreAdjuntoByte);
/*  78: 87 */           listaAdjuntos.add(attachmentPart);
/*  79:    */         }
/*  80: 90 */         ServicioEnvioEmail.DatosSMTP datosSMTP = (ServicioEnvioEmail.DatosSMTP)ServicioEnvioEmail.hmDatosSMTP.get(Integer.valueOf(idOrganizaion));
/*  81:    */         
/*  82: 92 */         System.out.println("-- MDBEnvioMail -- 1.1");
/*  83: 93 */         enviarEmail(datosSMTP.getSmtpFrom(), datosSMTP.getSmtpHost(), datosSMTP.getSmtpPort(), datosSMTP.getNombreUsuario(), datosSMTP
/*  84: 94 */           .getClave(), datosSMTP.isAutenticacion().booleanValue(), datosSMTP.getAutenticacionSSL().booleanValue(), para, tituloMensaje, mensajeCuerpo, archivos, mapaCid, datosSMTP
/*  85: 95 */           .getSmtpCC(), listaAdjuntos);
/*  86: 96 */         System.out.println("-- MDBEnvioMail -- 1.2");
/*  87:    */         
/*  88:    */ 
/*  89: 99 */         ServicioEnvioEmail.cmMensajeEmail.remove(Integer.valueOf(idMapMessage));
/*  90:100 */         this.servicioMensajeEmail.guardar(me);
/*  91:    */       }
/*  92:    */     }
/*  93:    */     catch (Exception ex)
/*  94:    */     {
/*  95:105 */       System.out.println(ex.getMessage());
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   @TransactionAttribute(TransactionAttributeType.NEVER)
/* 100:    */   private void enviarEmail(String smtpFrom, String smtpHost, String smtpPort, String nombreUsuario, String clave, boolean autenticacion, boolean autenticacionSSL, String para, String tituloMensaje, String mensajeCuerpo, String[] archivos, String[] mapaCid, String smtpCC, List<MimeBodyPart> listaAdjuntos)
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:115 */       if (listaAdjuntos == null) {
/* 105:116 */         listaAdjuntos = new ArrayList();
/* 106:    */       }
/* 107:119 */       Properties properties = new Properties();
/* 108:120 */       properties.put("mail.smtp.host", smtpHost);
/* 109:121 */       properties.put("mail.smtp.port", smtpPort);
/* 110:123 */       if (autenticacionSSL == true)
/* 111:    */       {
/* 112:124 */         properties.put("mail.smtp.socketFactory.port", smtpPort);
/* 113:125 */         properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
/* 114:    */       }
/* 115:    */       Session session;
/* 116:    */       Session session;
/* 117:129 */       if (autenticacion == true)
/* 118:    */       {
/* 119:130 */         properties.put("mail.smtp.auth", "true");
/* 120:131 */         Authenticator autenticator = new SMTPAuthenticator(nombreUsuario, clave);
/* 121:    */         
/* 122:133 */         session = Session.getInstance(properties, autenticator);
/* 123:    */       }
/* 124:    */       else
/* 125:    */       {
/* 126:135 */         session = Session.getInstance(properties, null);
/* 127:    */       }
/* 128:140 */       MimeMessage message = new MimeMessage(session);
/* 129:141 */       message.setFrom(new InternetAddress(smtpFrom));
/* 130:    */       
/* 131:143 */       String[] listaDestinatarios = para.split(";");
/* 132:144 */       for (destinatario : listaDestinatarios) {
/* 133:145 */         message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario.trim()));
/* 134:    */       }
/* 135:147 */       if ((smtpCC != null) && (!smtpCC.isEmpty())) {
/* 136:148 */         message.addRecipient(Message.RecipientType.CC, new InternetAddress(smtpCC.trim()));
/* 137:    */       }
/* 138:150 */       message.setSubject(tituloMensaje);
/* 139:151 */       message.setSentDate(new Date());
/* 140:    */       
/* 141:    */ 
/* 142:154 */       MimeBodyPart messagePart = new MimeBodyPart();
/* 143:155 */       messagePart.setContent(mensajeCuerpo, "text/html");
/* 144:    */       
/* 145:157 */       Object multipart = new MimeMultipart();
/* 146:158 */       ((Multipart)multipart).addBodyPart(messagePart);
/* 147:    */       
/* 148:    */ 
/* 149:161 */       Object localObject1 = mapaCid;String destinatario = localObject1.length;
/* 150:161 */       for (String str1 = 0; str1 < destinatario; str1++)
/* 151:    */       {
/* 152:161 */         String cid = localObject1[str1];
/* 153:162 */         String[] arayCid = cid.split(";");
/* 154:163 */         String cidname = arayCid[0];
/* 155:164 */         String pathname = arayCid[1];
/* 156:    */         
/* 157:166 */         DataSource fds = new FileDataSource(pathname);
/* 158:167 */         BodyPart messageBodyPart = new MimeBodyPart();
/* 159:168 */         messageBodyPart.setDataHandler(new DataHandler(fds));
/* 160:169 */         messageBodyPart.setHeader("Content-ID", "<" + cidname + ">");
/* 161:170 */         ((Multipart)multipart).addBodyPart(messageBodyPart);
/* 162:    */       }
/* 163:173 */       message.setContent((Multipart)multipart);
/* 164:    */       
/* 165:175 */       localObject1 = archivos;destinatario = localObject1.length;
/* 166:175 */       for (String str2 = 0; str2 < destinatario; str2++)
/* 167:    */       {
/* 168:175 */         String nombreArchivo = localObject1[str2];
/* 169:176 */         File file = new File(nombreArchivo.trim());
/* 170:177 */         if (file.exists())
/* 171:    */         {
/* 172:178 */           MimeBodyPart attachmentPart = new MimeBodyPart();
/* 173:179 */           FileDataSource fileDataSource = new FileDataSource(file);
/* 174:180 */           attachmentPart.setDataHandler(new DataHandler(fileDataSource));
/* 175:181 */           attachmentPart.setFileName(fileDataSource.getFile().getName());
/* 176:182 */           ((Multipart)multipart).addBodyPart(attachmentPart);
/* 177:    */         }
/* 178:    */         else
/* 179:    */         {
/* 180:184 */           System.out.println("Archivo no encontrado: " + nombreArchivo);
/* 181:    */         }
/* 182:    */       }
/* 183:187 */       for (localObject1 = listaAdjuntos.iterator(); ((Iterator)localObject1).hasNext();)
/* 184:    */       {
/* 185:187 */         MimeBodyPart attachmentPart = (MimeBodyPart)((Iterator)localObject1).next();
/* 186:188 */         ((Multipart)multipart).addBodyPart(attachmentPart);
/* 187:    */       }
/* 188:191 */       Transport.send(message);
/* 189:    */     }
/* 190:    */     catch (MessagingException e)
/* 191:    */     {
/* 192:194 */       e.printStackTrace();
/* 193:195 */       System.out.println("Error al enviar email + [Host:" + smtpHost + ", From:" + smtpFrom + "]");
/* 194:    */     }
/* 195:    */     catch (Exception e)
/* 196:    */     {
/* 197:197 */       e.printStackTrace();
/* 198:198 */       System.out.println("Error al enviar email + [Host:" + smtpHost + ", From:" + smtpFrom + "]");
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   private class SMTPAuthenticator
/* 203:    */     extends Authenticator
/* 204:    */   {
/* 205:    */     private String nombreUsuario;
/* 206:    */     private String clave;
/* 207:    */     
/* 208:    */     public SMTPAuthenticator(String nombreUsuario, String clave)
/* 209:    */     {
/* 210:208 */       this.nombreUsuario = nombreUsuario;
/* 211:209 */       this.clave = clave;
/* 212:    */     }
/* 213:    */     
/* 214:    */     public PasswordAuthentication getPasswordAuthentication()
/* 215:    */     {
/* 216:214 */       return new PasswordAuthentication(this.nombreUsuario, this.clave);
/* 217:    */     }
/* 218:    */   }
/* 219:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.jms.MDBEnvioMail
 * JD-Core Version:    0.7.0.1
 */