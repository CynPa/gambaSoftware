/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_finiquito_empleado")
/*  22:    */ public class DetalleFiniquitoEmpleado
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -3942804549505021448L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="detalle_finiquito_empleado", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_finiquito_empleado")
/*  29:    */   @Column(name="id_detalle_finiquito_empleado")
/*  30:    */   private int idDetalleFiniquitoEmpleado;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Temporal(TemporalType.DATE)
/*  36:    */   @Column(name="fecha", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private Date fecha;
/*  39:    */   @Column(name="valor", precision=12, scale=2)
/*  40:    */   @Digits(integer=12, fraction=2)
/*  41: 65 */   private BigDecimal valor = BigDecimal.ZERO;
/*  42:    */   @Column(name="indicador_impresion_sobre", nullable=false)
/*  43:    */   private boolean indicadorImpresionSobre;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_historico_empleado", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private HistoricoEmpleado historicoEmpleado;
/*  48:    */   
/*  49:    */   public DetalleFiniquitoEmpleado() {}
/*  50:    */   
/*  51:    */   public DetalleFiniquitoEmpleado(Integer idOrganizacion, Integer idSucursal, Date fecha, BigDecimal valor, boolean indicadorImpresionSobre)
/*  52:    */   {
/*  53: 96 */     this.idOrganizacion = idOrganizacion.intValue();
/*  54: 97 */     this.idSucursal = idSucursal.intValue();
/*  55: 98 */     this.fecha = fecha;
/*  56: 99 */     this.valor = valor;
/*  57:100 */     this.indicadorImpresionSobre = indicadorImpresionSobre;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62:110 */     return this.idDetalleFiniquitoEmpleado;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdDetalleFiniquitoEmpleado()
/*  66:    */   {
/*  67:119 */     return this.idDetalleFiniquitoEmpleado;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdDetalleFiniquitoEmpleado(int idDetalleFiniquitoEmpleado)
/*  71:    */   {
/*  72:129 */     this.idDetalleFiniquitoEmpleado = idDetalleFiniquitoEmpleado;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdOrganizacion()
/*  76:    */   {
/*  77:138 */     return this.idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:148 */     this.idOrganizacion = idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:157 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:167 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public BigDecimal getValor()
/*  96:    */   {
/*  97:171 */     return this.valor;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setValor(BigDecimal valor)
/* 101:    */   {
/* 102:175 */     this.valor = valor;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 106:    */   {
/* 107:179 */     return this.historicoEmpleado;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 111:    */   {
/* 112:183 */     this.historicoEmpleado = historicoEmpleado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Date getFecha()
/* 116:    */   {
/* 117:187 */     return this.fecha;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFecha(Date fecha)
/* 121:    */   {
/* 122:191 */     this.fecha = fecha;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isIndicadorImpresionSobre()
/* 126:    */   {
/* 127:195 */     return this.indicadorImpresionSobre;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setIndicadorImpresionSobre(boolean indicadorImpresionSobre)
/* 131:    */   {
/* 132:199 */     this.indicadorImpresionSobre = indicadorImpresionSobre;
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleFiniquitoEmpleado
 * JD-Core Version:    0.7.0.1
 */