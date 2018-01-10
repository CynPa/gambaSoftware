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
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_historico_empleado")
/*  23:    */ public class DetalleHistoricoEmpleado
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -3942804549505021448L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_historico_empleado", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_historico_empleado")
/*  30:    */   @Column(name="id_detalle_historico_empleado")
/*  31:    */   private int idDetalleHistoricoEmpleado;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Temporal(TemporalType.DATE)
/*  37:    */   @Column(name="fecha_inicio", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private Date fechaInicio;
/*  40:    */   @Temporal(TemporalType.DATE)
/*  41:    */   @Column(name="fecha_inicio_contrato_fijo", nullable=true)
/*  42:    */   private Date fechaInicioContratoFijo;
/*  43:    */   @Temporal(TemporalType.DATE)
/*  44:    */   @Column(name="fecha_fin", nullable=true)
/*  45:    */   private Date fechaFin;
/*  46:    */   @Column(name="horas_semana", nullable=false)
/*  47:    */   @Min(0L)
/*  48:    */   @NotNull
/*  49:    */   private int horasSemana;
/*  50:    */   @Column(name="porcentaje_capacidad_semanal", nullable=false, precision=12, scale=2)
/*  51:    */   @Digits(integer=12, fraction=2)
/*  52:    */   @Min(0L)
/*  53: 79 */   private BigDecimal porcentajeCapacidadSemanal = BigDecimal.ZERO;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_historico_empleado", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private HistoricoEmpleado historicoEmpleado;
/*  58:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  59:    */   @JoinColumn(name="id_tipo_contrato", nullable=false)
/*  60:    */   @NotNull
/*  61:    */   private TipoContrato tipoContrato;
/*  62:    */   @Column(name="numero_contrato", nullable=false, length=20)
/*  63:    */   @NotNull
/*  64: 97 */   private String numeroContrato = "";
/*  65:    */   
/*  66:    */   public int getId()
/*  67:    */   {
/*  68:122 */     return this.idDetalleHistoricoEmpleado;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdDetalleHistoricoEmpleado()
/*  72:    */   {
/*  73:131 */     return this.idDetalleHistoricoEmpleado;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdDetalleHistoricoEmpleado(int idDetalleHistoricoEmpleado)
/*  77:    */   {
/*  78:141 */     this.idDetalleHistoricoEmpleado = idDetalleHistoricoEmpleado;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdOrganizacion()
/*  82:    */   {
/*  83:150 */     return this.idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdOrganizacion(int idOrganizacion)
/*  87:    */   {
/*  88:160 */     this.idOrganizacion = idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIdSucursal()
/*  92:    */   {
/*  93:169 */     return this.idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIdSucursal(int idSucursal)
/*  97:    */   {
/*  98:179 */     this.idSucursal = idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Date getFechaInicio()
/* 102:    */   {
/* 103:188 */     return this.fechaInicio;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setFechaInicio(Date fechaInicio)
/* 107:    */   {
/* 108:198 */     this.fechaInicio = fechaInicio;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Date getFechaFin()
/* 112:    */   {
/* 113:207 */     return this.fechaFin;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setFechaFin(Date fechaFin)
/* 117:    */   {
/* 118:217 */     this.fechaFin = fechaFin;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public HistoricoEmpleado getHistoricoEmpleado()
/* 122:    */   {
/* 123:226 */     return this.historicoEmpleado;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setHistoricoEmpleado(HistoricoEmpleado historicoEmpleado)
/* 127:    */   {
/* 128:236 */     this.historicoEmpleado = historicoEmpleado;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public TipoContrato getTipoContrato()
/* 132:    */   {
/* 133:243 */     return this.tipoContrato;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setTipoContrato(TipoContrato tipoContrato)
/* 137:    */   {
/* 138:250 */     this.tipoContrato = tipoContrato;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getNumeroContrato()
/* 142:    */   {
/* 143:257 */     return this.numeroContrato;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setNumeroContrato(String numeroContrato)
/* 147:    */   {
/* 148:264 */     this.numeroContrato = numeroContrato;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getHorasSemana()
/* 152:    */   {
/* 153:273 */     return this.horasSemana;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setHorasSemana(int horasSemana)
/* 157:    */   {
/* 158:283 */     this.horasSemana = horasSemana;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public BigDecimal getPorcentajeCapacidadSemanal()
/* 162:    */   {
/* 163:292 */     return this.porcentajeCapacidadSemanal;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setPorcentajeCapacidadSemanal(BigDecimal porcentajeCapacidadSemanal)
/* 167:    */   {
/* 168:302 */     this.porcentajeCapacidadSemanal = porcentajeCapacidadSemanal;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Date getFechaInicioContratoFijo()
/* 172:    */   {
/* 173:306 */     return this.fechaInicioContratoFijo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setFechaInicioContratoFijo(Date fechaInicioContratoFijo)
/* 177:    */   {
/* 178:310 */     this.fechaInicioContratoFijo = fechaInicioContratoFijo;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleHistoricoEmpleado
 * JD-Core Version:    0.7.0.1
 */