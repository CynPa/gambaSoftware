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
/*  12:    */ @Table(name="tmp_reporte_anticipo_cliente")
/*  13:    */ public class ReporteAnticipoCliente
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 1L;
/*  17:    */   @Id
/*  18:    */   @Column(name="id_anticipo_cliente")
/*  19:    */   private int idAnticipoCliente;
/*  20:    */   @Column(name="numero_anticipo")
/*  21:    */   private String numeroAnticipo;
/*  22:    */   @Column(name="nombre_empresa")
/*  23:    */   private String nombreEmpresa;
/*  24:    */   @Column(name="nombre_forma_pago")
/*  25:    */   private String nombreFormaPago;
/*  26:    */   @Column(name="fecha")
/*  27:    */   private Date fecha;
/*  28:    */   @Column(name="valor")
/*  29:    */   private BigDecimal valor;
/*  30:    */   @Column(name="saldo")
/*  31:    */   private BigDecimal saldo;
/*  32:    */   @Column(name="numero_asiento")
/*  33:    */   private String numeroAsiento;
/*  34:    */   
/*  35:    */   public ReporteAnticipoCliente(int idAnticipoCliente, String numeroAnticipo, String nombreEmpresa, String nombreFormaPago, Date fecha, BigDecimal valor, BigDecimal saldo, String numeroAsiento)
/*  36:    */   {
/*  37: 65 */     this.idAnticipoCliente = idAnticipoCliente;
/*  38: 66 */     this.numeroAnticipo = numeroAnticipo;
/*  39: 67 */     this.nombreEmpresa = nombreEmpresa;
/*  40: 68 */     this.nombreFormaPago = nombreFormaPago;
/*  41: 69 */     this.fecha = fecha;
/*  42: 70 */     this.valor = valor;
/*  43: 71 */     this.saldo = saldo;
/*  44: 72 */     this.numeroAsiento = numeroAsiento;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ReporteAnticipoCliente(String nombreEmpresa, BigDecimal saldo)
/*  48:    */   {
/*  49: 76 */     this.nombreEmpresa = nombreEmpresa;
/*  50: 77 */     this.saldo = saldo;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdAnticipoCliente()
/*  54:    */   {
/*  55: 81 */     return this.idAnticipoCliente;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdAnticipoCliente(int idAnticipoCliente)
/*  59:    */   {
/*  60: 85 */     this.idAnticipoCliente = idAnticipoCliente;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getNumeroAnticipo()
/*  64:    */   {
/*  65: 89 */     return this.numeroAnticipo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setNumeroAnticipo(String numeroAnticipo)
/*  69:    */   {
/*  70: 93 */     this.numeroAnticipo = numeroAnticipo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getNombreEmpresa()
/*  74:    */   {
/*  75: 97 */     return this.nombreEmpresa;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setNombreEmpresa(String nombreEmpresa)
/*  79:    */   {
/*  80:101 */     this.nombreEmpresa = nombreEmpresa;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getNombreFormaPago()
/*  84:    */   {
/*  85:105 */     return this.nombreFormaPago;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setNombreFormaPago(String nombreFormaPago)
/*  89:    */   {
/*  90:109 */     this.nombreFormaPago = nombreFormaPago;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Date getFecha()
/*  94:    */   {
/*  95:113 */     return this.fecha;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setFecha(Date fecha)
/*  99:    */   {
/* 100:117 */     this.fecha = fecha;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public BigDecimal getValor()
/* 104:    */   {
/* 105:121 */     return this.valor;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setValor(BigDecimal valor)
/* 109:    */   {
/* 110:125 */     this.valor = valor;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public BigDecimal getSaldo()
/* 114:    */   {
/* 115:129 */     return this.saldo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setSaldo(BigDecimal saldo)
/* 119:    */   {
/* 120:133 */     this.saldo = saldo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getNumeroAsiento()
/* 124:    */   {
/* 125:137 */     return this.numeroAsiento;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setNumeroAsiento(String numeroAsiento)
/* 129:    */   {
/* 130:141 */     this.numeroAsiento = numeroAsiento;
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteAnticipoCliente
 * JD-Core Version:    0.7.0.1
 */