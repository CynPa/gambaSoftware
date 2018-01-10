/*   1:    */ package com.asinfo.as2.rs.inventario.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ 
/*   7:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   8:    */ public class ImpuestoResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idImpuesto;
/*  12:    */   private int idOrganizacion;
/*  13:    */   private String codigo;
/*  14:    */   private String nombre;
/*  15:    */   private boolean indicadorImpuestoTributario;
/*  16:    */   @Deprecated
/*  17:    */   private boolean indicadorImpuestoIce;
/*  18:    */   private boolean indicadorNoObjetoIVA;
/*  19:    */   private boolean indicadorAfectaCantidades;
/*  20:    */   private BigDecimal porcentajeImpuesto;
/*  21:    */   private boolean indicadorCompra;
/*  22:    */   private boolean indicadorVenta;
/*  23:    */   
/*  24:    */   public Integer getIdImpuesto()
/*  25:    */   {
/*  26: 37 */     return this.idImpuesto;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setIdImpuesto(Integer idImpuesto)
/*  30:    */   {
/*  31: 41 */     this.idImpuesto = idImpuesto;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getIdOrganizacion()
/*  35:    */   {
/*  36: 45 */     return this.idOrganizacion;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setIdOrganizacion(int idOrganizacion)
/*  40:    */   {
/*  41: 49 */     this.idOrganizacion = idOrganizacion;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getCodigo()
/*  45:    */   {
/*  46: 53 */     return this.codigo;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setCodigo(String codigo)
/*  50:    */   {
/*  51: 57 */     this.codigo = codigo;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getNombre()
/*  55:    */   {
/*  56: 61 */     return this.nombre;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setNombre(String nombre)
/*  60:    */   {
/*  61: 65 */     this.nombre = nombre;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isIndicadorImpuestoTributario()
/*  65:    */   {
/*  66: 69 */     return this.indicadorImpuestoTributario;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIndicadorImpuestoTributario(boolean indicadorImpuestoTributario)
/*  70:    */   {
/*  71: 73 */     this.indicadorImpuestoTributario = indicadorImpuestoTributario;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isIndicadorImpuestoIce()
/*  75:    */   {
/*  76: 77 */     return this.indicadorImpuestoIce;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIndicadorImpuestoIce(boolean indicadorImpuestoIce)
/*  80:    */   {
/*  81: 81 */     this.indicadorImpuestoIce = indicadorImpuestoIce;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isIndicadorNoObjetoIVA()
/*  85:    */   {
/*  86: 85 */     return this.indicadorNoObjetoIVA;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIndicadorNoObjetoIVA(boolean indicadorNoObjetoIVA)
/*  90:    */   {
/*  91: 89 */     this.indicadorNoObjetoIVA = indicadorNoObjetoIVA;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isIndicadorAfectaCantidades()
/*  95:    */   {
/*  96: 93 */     return this.indicadorAfectaCantidades;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIndicadorAfectaCantidades(boolean indicadorAfectaCantidades)
/* 100:    */   {
/* 101: 97 */     this.indicadorAfectaCantidades = indicadorAfectaCantidades;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public BigDecimal getPorcentajeImpuesto()
/* 105:    */   {
/* 106:101 */     return this.porcentajeImpuesto;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setPorcentajeImpuesto(BigDecimal porcentajeImpuesto)
/* 110:    */   {
/* 111:105 */     this.porcentajeImpuesto = porcentajeImpuesto;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isIndicadorCompra()
/* 115:    */   {
/* 116:109 */     return this.indicadorCompra;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIndicadorCompra(boolean indicadorCompra)
/* 120:    */   {
/* 121:113 */     this.indicadorCompra = indicadorCompra;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isIndicadorVenta()
/* 125:    */   {
/* 126:117 */     return this.indicadorVenta;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setIndicadorVenta(boolean indicadorVenta)
/* 130:    */   {
/* 131:121 */     this.indicadorVenta = indicadorVenta;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.inventario.dto.ImpuestoResponseDto
 * JD-Core Version:    0.7.0.1
 */