/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import javax.xml.bind.annotation.XmlElement;
/*  6:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  7:   */ 
/*  8:   */ @XmlRootElement(name="detallesAdicionales")
/*  9:   */ public class DetalleAdicionalJaxb
/* 10:   */ {
/* 11:   */   private List<DetAdicionalJaxb> listaDetAdicional;
/* 12:   */   
/* 13:   */   @XmlElement(name="detAdicional")
/* 14:   */   public List<DetAdicionalJaxb> getListaDetAdicional()
/* 15:   */   {
/* 16:31 */     if (this.listaDetAdicional == null) {
/* 17:32 */       this.listaDetAdicional = new ArrayList();
/* 18:   */     }
/* 19:34 */     return this.listaDetAdicional;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setListaDetAdicional(List<DetAdicionalJaxb> listaDetAdicional)
/* 23:   */   {
/* 24:38 */     this.listaDetAdicional = listaDetAdicional;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.DetalleAdicionalJaxb
 * JD-Core Version:    0.7.0.1
 */