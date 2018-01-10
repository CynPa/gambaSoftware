/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ 
/*  7:   */ @XmlRootElement(name="destinatarios")
/*  8:   */ public class DestinatariosJaxb
/*  9:   */ {
/* 10:   */   private List<DestinatarioJaxb> listaDestinatario;
/* 11:   */   
/* 12:   */   @XmlElement(name="destinatario")
/* 13:   */   public List<DestinatarioJaxb> getListaDestinatario()
/* 14:   */   {
/* 15:29 */     return this.listaDestinatario;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setListaDestinatario(List<DestinatarioJaxb> listaDestinatario)
/* 19:   */   {
/* 20:33 */     this.listaDestinatario = listaDestinatario;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.DestinatariosJaxb
 * JD-Core Version:    0.7.0.1
 */