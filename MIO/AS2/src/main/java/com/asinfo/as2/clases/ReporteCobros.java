/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ 
/*  11:    */ @Entity
/*  12:    */ @Table(name="tmp_reporte_cobros")
/*  13:    */ public class ReporteCobros
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 1L;
/*  17:    */   @Id
/*  18:    */   @Column(name="id_reporte_cobros")
/*  19:    */   private int idReporteCobros;
/*  20:    */   @Column(name="fecha_contabilizacion")
/*  21:    */   private Date fechaContabilizacion;
/*  22:    */   @Column(name="fecha")
/*  23:    */   private Date fecha;
/*  24:    */   @Column(name="numero")
/*  25:    */   private String numero;
/*  26:    */   @Column(name="asiento")
/*  27:    */   private String asiento;
/*  28:    */   @Column(name="valor")
/*  29:    */   private BigDecimal valor;
/*  30:    */   @Column(name="factura")
/*  31:    */   private String factura;
/*  32:    */   @Column(name="identificador_cobro_anticipo")
/*  33:    */   private int identificadorCobroAnticipo;
/*  34:    */   
/*  35:    */   public ReporteCobros(int idReporteCobros, Date fechaContabilizacion, Date fecha, String numero, int identificadorCobroAnticipo, BigDecimal valor)
/*  36:    */   {
/*  37: 65 */     this.idReporteCobros = idReporteCobros;
/*  38: 66 */     this.fechaContabilizacion = fechaContabilizacion;
/*  39: 67 */     this.fecha = fecha;
/*  40: 68 */     this.numero = numero;
/*  41: 69 */     this.identificadorCobroAnticipo = identificadorCobroAnticipo;
/*  42: 70 */     this.valor = valor;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getIdReporteCobros()
/*  46:    */   {
/*  47: 75 */     return this.idReporteCobros;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setIdReporteCobros(int idReporteCobros)
/*  51:    */   {
/*  52: 80 */     this.idReporteCobros = idReporteCobros;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Date getFechaContabilizacion()
/*  56:    */   {
/*  57: 84 */     return this.fechaContabilizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setFechaContabilizacion(Date fechaContabilizacion)
/*  61:    */   {
/*  62: 88 */     this.fechaContabilizacion = fechaContabilizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Date getFecha()
/*  66:    */   {
/*  67: 92 */     return this.fecha;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setFecha(Date fecha)
/*  71:    */   {
/*  72: 96 */     this.fecha = fecha;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getNumero()
/*  76:    */   {
/*  77:100 */     return this.numero;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setNumero(String numero)
/*  81:    */   {
/*  82:104 */     this.numero = numero;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getAsiento()
/*  86:    */   {
/*  87:108 */     return this.asiento;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setAsiento(String asiento)
/*  91:    */   {
/*  92:112 */     this.asiento = asiento;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public BigDecimal getValor()
/*  96:    */   {
/*  97:116 */     return this.valor;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setValor(BigDecimal valor)
/* 101:    */   {
/* 102:120 */     this.valor = valor;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getFactura()
/* 106:    */   {
/* 107:124 */     return this.factura;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setFactura(String factura)
/* 111:    */   {
/* 112:128 */     this.factura = factura;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getIdentificadorCobroAnticipo()
/* 116:    */   {
/* 117:132 */     return this.identificadorCobroAnticipo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setIdentificadorCobroAnticipo(int identificadorCobroAnticipo)
/* 121:    */   {
/* 122:136 */     this.identificadorCobroAnticipo = identificadorCobroAnticipo;
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteCobros
 * JD-Core Version:    0.7.0.1
 */