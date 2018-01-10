/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.persistence.Transient;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="tmp_saldo_negativo_producto")
/*  16:    */ public class SaldoNegativoProducto
/*  17:    */ {
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="saldo_negativo_producto", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="saldo_negativo_producto")
/*  21:    */   @Column(name="id_saldo_negativo_producto")
/*  22:    */   private Integer idSaldoNegativoProducto;
/*  23:    */   @Transient
/*  24:    */   private String codigoProducto;
/*  25:    */   @Transient
/*  26:    */   private String codigoAlternoProducto;
/*  27:    */   @Transient
/*  28:    */   private String nombreProducto;
/*  29:    */   @Transient
/*  30:    */   private String nombreComercialProducto;
/*  31:    */   @Transient
/*  32:    */   private String codigoBodega;
/*  33:    */   @Transient
/*  34:    */   private String nombreBodega;
/*  35:    */   @Transient
/*  36:    */   private Date fecha;
/*  37:    */   @Transient
/*  38: 69 */   private BigDecimal saldo = BigDecimal.ZERO;
/*  39:    */   
/*  40:    */   public SaldoNegativoProducto() {}
/*  41:    */   
/*  42:    */   public SaldoNegativoProducto(String codigoProducto, String codigoAlternoProducto, String nombreProducto, String nombreComercialProducto, String codigoBodega, String nombreBodega, Date fecha, BigDecimal saldo)
/*  43:    */   {
/*  44: 81 */     this.codigoProducto = codigoProducto;
/*  45: 82 */     this.codigoAlternoProducto = codigoAlternoProducto;
/*  46: 83 */     this.nombreProducto = nombreProducto;
/*  47: 84 */     this.nombreComercialProducto = nombreComercialProducto;
/*  48: 85 */     this.codigoBodega = codigoBodega;
/*  49: 86 */     this.nombreBodega = nombreBodega;
/*  50: 87 */     this.fecha = fecha;
/*  51: 88 */     this.saldo = saldo;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Integer getIdReporteSaldoProducto()
/*  55:    */   {
/*  56: 92 */     return this.idSaldoNegativoProducto;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdReporteSaldoProducto(Integer idReporteSaldoProducto)
/*  60:    */   {
/*  61: 96 */     this.idSaldoNegativoProducto = idReporteSaldoProducto;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getCodigoProducto()
/*  65:    */   {
/*  66:100 */     return this.codigoProducto;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setCodigoProducto(String codigoProducto)
/*  70:    */   {
/*  71:104 */     this.codigoProducto = codigoProducto;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getCodigoAlternoProducto()
/*  75:    */   {
/*  76:108 */     return this.codigoAlternoProducto;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setCodigoAlternoProducto(String codigoAlternoProducto)
/*  80:    */   {
/*  81:112 */     this.codigoAlternoProducto = codigoAlternoProducto;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getNombreProducto()
/*  85:    */   {
/*  86:116 */     return this.nombreProducto;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setNombreProducto(String nombreProducto)
/*  90:    */   {
/*  91:120 */     this.nombreProducto = nombreProducto;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getNombreComercialProducto()
/*  95:    */   {
/*  96:124 */     return this.nombreComercialProducto;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setNombreComercialProducto(String nombreComercialProducto)
/* 100:    */   {
/* 101:128 */     this.nombreComercialProducto = nombreComercialProducto;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getCodigoBodega()
/* 105:    */   {
/* 106:132 */     return this.codigoBodega;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setCodigoBodega(String codigoBodega)
/* 110:    */   {
/* 111:136 */     this.codigoBodega = codigoBodega;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getNombreBodega()
/* 115:    */   {
/* 116:140 */     return this.nombreBodega;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setNombreBodega(String nombreBodega)
/* 120:    */   {
/* 121:144 */     this.nombreBodega = nombreBodega;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Date getFecha()
/* 125:    */   {
/* 126:148 */     return this.fecha;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setFecha(Date fecha)
/* 130:    */   {
/* 131:152 */     this.fecha = fecha;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal getSaldo()
/* 135:    */   {
/* 136:156 */     return this.saldo;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setSaldo(BigDecimal saldo)
/* 140:    */   {
/* 141:160 */     this.saldo = saldo;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.SaldoNegativoProducto
 * JD-Core Version:    0.7.0.1
 */