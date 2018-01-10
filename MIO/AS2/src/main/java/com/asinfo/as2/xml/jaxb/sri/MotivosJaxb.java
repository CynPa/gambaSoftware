/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ 
/*  7:   */ @XmlRootElement(name="motivos")
/*  8:   */ public class MotivosJaxb
/*  9:   */ {
/* 10:   */   private List<MotivoJaxb> listaMotivo;
/* 11:   */   
/* 12:   */   @XmlElement(name="motivo")
/* 13:   */   public List<MotivoJaxb> getListaMotivo()
/* 14:   */   {
/* 15:29 */     return this.listaMotivo;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setListaMotivo(List<MotivoJaxb> listaMotivo)
/* 19:   */   {
/* 20:33 */     this.listaMotivo = listaMotivo;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.MotivosJaxb
 * JD-Core Version:    0.7.0.1
 */