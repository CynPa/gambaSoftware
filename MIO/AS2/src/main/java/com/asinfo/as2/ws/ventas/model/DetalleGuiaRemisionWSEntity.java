/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ 
/*   6:    */ public class DetalleGuiaRemisionWSEntity
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1L;
/*  10:    */   private Long idSucursal;
/*  11:    */   private Long idOrganizacion;
/*  12:    */   private Long idProducto;
/*  13:    */   private Long idGuiaRemision;
/*  14: 14 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  15: 15 */   private String descripcion = "";
/*  16:    */   private String codigoProducto;
/*  17:    */   private String nombre1;
/*  18:    */   private String valor1;
/*  19:    */   private String nombre2;
/*  20:    */   private String valor2;
/*  21:    */   private String nombre3;
/*  22:    */   private String valor3;
/*  23:    */   
/*  24:    */   public Long getIdSucursal()
/*  25:    */   {
/*  26: 29 */     return this.idSucursal;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setIdSucursal(Long idSucursal)
/*  30:    */   {
/*  31: 37 */     this.idSucursal = idSucursal;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Long getIdOrganizacion()
/*  35:    */   {
/*  36: 44 */     return this.idOrganizacion;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setIdOrganizacion(Long idOrganizacion)
/*  40:    */   {
/*  41: 52 */     this.idOrganizacion = idOrganizacion;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Long getIdProducto()
/*  45:    */   {
/*  46: 59 */     return this.idProducto;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setIdProducto(Long idProducto)
/*  50:    */   {
/*  51: 67 */     this.idProducto = idProducto;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Long getIdGuiaRemision()
/*  55:    */   {
/*  56: 74 */     return this.idGuiaRemision;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdGuiaRemision(Long idGuiaRemision)
/*  60:    */   {
/*  61: 82 */     this.idGuiaRemision = idGuiaRemision;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public BigDecimal getCantidad()
/*  65:    */   {
/*  66: 89 */     return this.cantidad;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setCantidad(BigDecimal cantidad)
/*  70:    */   {
/*  71: 97 */     this.cantidad = cantidad;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getDescripcion()
/*  75:    */   {
/*  76:104 */     return this.descripcion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setDescripcion(String descripcion)
/*  80:    */   {
/*  81:112 */     this.descripcion = descripcion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getCodigoProducto()
/*  85:    */   {
/*  86:119 */     return this.codigoProducto;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setCodigoProducto(String codigoProducto)
/*  90:    */   {
/*  91:126 */     this.codigoProducto = codigoProducto;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getNombre1()
/*  95:    */   {
/*  96:133 */     return this.nombre1;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setNombre1(String nombre1)
/* 100:    */   {
/* 101:140 */     this.nombre1 = nombre1;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getValor1()
/* 105:    */   {
/* 106:147 */     return this.valor1;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setValor1(String valor1)
/* 110:    */   {
/* 111:154 */     this.valor1 = valor1;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getNombre2()
/* 115:    */   {
/* 116:161 */     return this.nombre2;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setNombre2(String nombre2)
/* 120:    */   {
/* 121:168 */     this.nombre2 = nombre2;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getValor2()
/* 125:    */   {
/* 126:175 */     return this.valor2;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setValor2(String valor2)
/* 130:    */   {
/* 131:182 */     this.valor2 = valor2;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getNombre3()
/* 135:    */   {
/* 136:189 */     return this.nombre3;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setNombre3(String nombre3)
/* 140:    */   {
/* 141:196 */     this.nombre3 = nombre3;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getValor3()
/* 145:    */   {
/* 146:203 */     return this.valor3;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setValor3(String valor3)
/* 150:    */   {
/* 151:210 */     this.valor3 = valor3;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.DetalleGuiaRemisionWSEntity
 * JD-Core Version:    0.7.0.1
 */