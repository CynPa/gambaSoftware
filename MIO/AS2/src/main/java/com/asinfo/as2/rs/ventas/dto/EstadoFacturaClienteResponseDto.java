/*   1:    */ package com.asinfo.as2.rs.ventas.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ public class EstadoFacturaClienteResponseDto
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private Integer idFacturaCliente;
/*  10:    */   private String numero;
/*  11:    */   private Estado estado;
/*  12:    */   private String autorizacion;
/*  13:    */   private String fechaAutorizacion;
/*  14:    */   private String claveAcceso;
/*  15:    */   private String despacho;
/*  16:    */   private int idDespacho;
/*  17: 26 */   private int hashCode = 0;
/*  18:    */   
/*  19:    */   public int getHashCode()
/*  20:    */   {
/*  21: 29 */     this.hashCode = hashCode();
/*  22: 30 */     return this.hashCode;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int hashCode()
/*  26:    */   {
/*  27: 35 */     int hash = 1;
/*  28: 36 */     hash += hash * 41 + (this.idFacturaCliente + "").hashCode();
/*  29: 37 */     hash += hash * 12 + (this.numero + "").hashCode();
/*  30: 38 */     hash += hash * 53 + (this.autorizacion + "").hashCode();
/*  31: 39 */     hash += hash * 16 + (this.fechaAutorizacion + "").hashCode();
/*  32: 40 */     hash += hash * 25 + (this.claveAcceso + "").hashCode();
/*  33: 41 */     hash += hash * 25 + (this.estado + "").hashCode();
/*  34: 42 */     hash += hash * 21 + (this.despacho + "").hashCode();
/*  35:    */     
/*  36: 44 */     return hash;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Integer getIdFacturaCliente()
/*  40:    */   {
/*  41: 48 */     return this.idFacturaCliente;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIdFacturaCliente(Integer idFacturaCliente)
/*  45:    */   {
/*  46: 52 */     this.idFacturaCliente = idFacturaCliente;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getNumero()
/*  50:    */   {
/*  51: 56 */     return this.numero;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setNumero(String numero)
/*  55:    */   {
/*  56: 60 */     this.numero = numero;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Estado getEstado()
/*  60:    */   {
/*  61: 64 */     return this.estado;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setEstado(Estado estado)
/*  65:    */   {
/*  66: 68 */     this.estado = estado;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getAutorizacion()
/*  70:    */   {
/*  71: 72 */     return this.autorizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setAutorizacion(String autorizacion)
/*  75:    */   {
/*  76: 76 */     this.autorizacion = autorizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getFechaAutorizacion()
/*  80:    */   {
/*  81: 80 */     return this.fechaAutorizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setFechaAutorizacion(String fechaAutorizacion)
/*  85:    */   {
/*  86: 84 */     this.fechaAutorizacion = fechaAutorizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getClaveAcceso()
/*  90:    */   {
/*  91: 88 */     return this.claveAcceso;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setClaveAcceso(String claveAcceso)
/*  95:    */   {
/*  96: 92 */     this.claveAcceso = claveAcceso;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setHashCode(int hashCode)
/* 100:    */   {
/* 101: 96 */     this.hashCode = hashCode;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getDespacho()
/* 105:    */   {
/* 106:100 */     return this.despacho;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDespacho(String despacho)
/* 110:    */   {
/* 111:104 */     this.despacho = despacho;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getIdDespacho()
/* 115:    */   {
/* 116:108 */     return this.idDespacho;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdDespacho(int idDespacho)
/* 120:    */   {
/* 121:112 */     this.idDespacho = idDespacho;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.ventas.dto.EstadoFacturaClienteResponseDto
 * JD-Core Version:    0.7.0.1
 */