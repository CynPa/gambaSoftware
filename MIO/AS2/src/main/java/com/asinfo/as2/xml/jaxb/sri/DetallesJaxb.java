/*  1:   */ package com.asinfo.as2.xml.jaxb.sri;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import javax.xml.bind.annotation.XmlElement;
/*  5:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  6:   */ 
/*  7:   */ @XmlRootElement(name="detalles")
/*  8:   */ public class DetallesJaxb
/*  9:   */ {
/* 10:   */   private List<DetalleJaxb> listaDetalle;
/* 11:   */   
/* 12:   */   @XmlElement(name="detalle")
/* 13:   */   public List<DetalleJaxb> getListaDetalle()
/* 14:   */   {
/* 15:29 */     return this.listaDetalle;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setListaDetalle(List<DetalleJaxb> listaDetalle)
/* 19:   */   {
/* 20:33 */     this.listaDetalle = listaDetalle;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.xml.jaxb.sri.DetallesJaxb
 * JD-Core Version:    0.7.0.1
 */