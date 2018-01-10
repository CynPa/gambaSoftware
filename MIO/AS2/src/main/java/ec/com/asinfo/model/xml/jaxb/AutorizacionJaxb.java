/*  1:   */ package ec.com.asinfo.model.xml.jaxb;
/*  2:   */ 
/*  3:   */ import javax.xml.bind.annotation.XmlElement;
/*  4:   */ import javax.xml.bind.annotation.XmlRootElement;
/*  5:   */ import javax.xml.bind.annotation.XmlType;
/*  6:   */ 
/*  7:   */ @XmlRootElement(name="autorizacion")
/*  8:   */ @XmlType(propOrder={"estado", "numeroAutorizacion", "fechaAutorizacion", "ambiente", "comprobante"})
/*  9:   */ public class AutorizacionJaxb
/* 10:   */ {
/* 11:   */   private String estado;
/* 12:   */   private String numeroAutorizacion;
/* 13:   */   private String fechaAutorizacion;
/* 14:   */   private String ambiente;
/* 15:   */   private String comprobante;
/* 16:   */   
/* 17:   */   @XmlElement(name="estado")
/* 18:   */   public String getEstado()
/* 19:   */   {
/* 20:27 */     return this.estado;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setEstado(String estado)
/* 24:   */   {
/* 25:31 */     this.estado = estado;
/* 26:   */   }
/* 27:   */   
/* 28:   */   @XmlElement(name="numeroAutorizacion")
/* 29:   */   public String getNumeroAutorizacion()
/* 30:   */   {
/* 31:36 */     return this.numeroAutorizacion;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setNumeroAutorizacion(String numeroAutorizacion)
/* 35:   */   {
/* 36:40 */     this.numeroAutorizacion = numeroAutorizacion;
/* 37:   */   }
/* 38:   */   
/* 39:   */   @XmlElement(name="fechaAutorizacion")
/* 40:   */   public String getFechaAutorizacion()
/* 41:   */   {
/* 42:45 */     return this.fechaAutorizacion;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setFechaAutorizacion(String fechaAutorizacion)
/* 46:   */   {
/* 47:49 */     this.fechaAutorizacion = fechaAutorizacion;
/* 48:   */   }
/* 49:   */   
/* 50:   */   @XmlElement(name="ambiente")
/* 51:   */   public String getAmbiente()
/* 52:   */   {
/* 53:54 */     return this.ambiente;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setAmbiente(String ambiente)
/* 57:   */   {
/* 58:58 */     this.ambiente = ambiente;
/* 59:   */   }
/* 60:   */   
/* 61:   */   @XmlElement(name="comprobante")
/* 62:   */   public String getComprobante()
/* 63:   */   {
/* 64:63 */     return this.comprobante;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setComprobante(String comprobante)
/* 68:   */   {
/* 69:67 */     this.comprobante = comprobante;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.com.asinfo.model.xml.jaxb.AutorizacionJaxb
 * JD-Core Version:    0.7.0.1
 */