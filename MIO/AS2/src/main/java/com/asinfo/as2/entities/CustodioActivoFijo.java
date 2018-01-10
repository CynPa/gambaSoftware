/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.CustodioActivoFijoListener;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EntityListeners;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="custodio_activo_fijo")
/*  24:    */ @EntityListeners({CustodioActivoFijoListener.class})
/*  25:    */ public class CustodioActivoFijo
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="custodio_activo_fijo", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="custodio_activo_fijo")
/*  32:    */   @Column(name="id_custodio_activo_fijo")
/*  33:    */   private int idCustodioActivoFijo;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="fecha_inicio", nullable=false)
/*  39:    */   @Temporal(TemporalType.DATE)
/*  40:    */   @NotNull
/*  41:    */   private Date fechaInicio;
/*  42:    */   @Column(name="fecha_fin", nullable=true)
/*  43:    */   @Temporal(TemporalType.DATE)
/*  44:    */   private Date fechaFin;
/*  45:    */   @Column(name="descripcion", nullable=true)
/*  46:    */   @Size(max=200)
/*  47:    */   private String descripcion;
/*  48:    */   @Column(name="activo", nullable=false)
/*  49:    */   private boolean activo;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_activo_fijo", nullable=false)
/*  52:    */   private ActivoFijo activoFijo;
/*  53:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  55:    */   private Empleado empleado;
/*  56:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  57:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  58:    */   private Empresa empresa;
/*  59:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  60:    */   @JoinColumn(name="id_ubicacion_activo", nullable=false)
/*  61:    */   private UbicacionActivo ubicacionActivo;
/*  62:    */   @OneToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_custodio_activo_fijo_anterior", nullable=true)
/*  64:    */   private CustodioActivoFijo custodioActivoFijoAnterior;
/*  65:    */   
/*  66:    */   public int getIdCustodioActivoFijo()
/*  67:    */   {
/*  68:119 */     return this.idCustodioActivoFijo;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdCustodioActivoFijo(int idCustodioActivoFijo)
/*  72:    */   {
/*  73:129 */     this.idCustodioActivoFijo = idCustodioActivoFijo;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdOrganizacion()
/*  77:    */   {
/*  78:138 */     return this.idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdOrganizacion(int idOrganizacion)
/*  82:    */   {
/*  83:148 */     this.idOrganizacion = idOrganizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdSucursal()
/*  87:    */   {
/*  88:157 */     return this.idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdSucursal(int idSucursal)
/*  92:    */   {
/*  93:167 */     this.idSucursal = idSucursal;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Date getFechaInicio()
/*  97:    */   {
/*  98:176 */     return this.fechaInicio;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setFechaInicio(Date fechaInicio)
/* 102:    */   {
/* 103:186 */     this.fechaInicio = fechaInicio;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Date getFechaFin()
/* 107:    */   {
/* 108:195 */     return this.fechaFin;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setFechaFin(Date fechaFin)
/* 112:    */   {
/* 113:205 */     this.fechaFin = fechaFin;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDescripcion()
/* 117:    */   {
/* 118:214 */     return this.descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDescripcion(String descripcion)
/* 122:    */   {
/* 123:224 */     this.descripcion = descripcion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ActivoFijo getActivoFijo()
/* 127:    */   {
/* 128:233 */     return this.activoFijo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 132:    */   {
/* 133:243 */     this.activoFijo = activoFijo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Empleado getEmpleado()
/* 137:    */   {
/* 138:252 */     return this.empleado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setEmpleado(Empleado empleado)
/* 142:    */   {
/* 143:262 */     this.empleado = empleado;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public UbicacionActivo getUbicacionActivo()
/* 147:    */   {
/* 148:271 */     return this.ubicacionActivo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setUbicacionActivo(UbicacionActivo ubicacionActivo)
/* 152:    */   {
/* 153:281 */     this.ubicacionActivo = ubicacionActivo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isActivo()
/* 157:    */   {
/* 158:290 */     return this.activo;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setActivo(boolean activo)
/* 162:    */   {
/* 163:300 */     this.activo = activo;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public CustodioActivoFijo getCustodioActivoFijoAnterior()
/* 167:    */   {
/* 168:309 */     return this.custodioActivoFijoAnterior;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setCustodioActivoFijoAnterior(CustodioActivoFijo custodioActivoFijoAnterior)
/* 172:    */   {
/* 173:319 */     this.custodioActivoFijoAnterior = custodioActivoFijoAnterior;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getId()
/* 177:    */   {
/* 178:329 */     return this.idCustodioActivoFijo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Empresa getEmpresa()
/* 182:    */   {
/* 183:333 */     return this.empresa;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setEmpresa(Empresa empresa)
/* 187:    */   {
/* 188:337 */     this.empresa = empresa;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CustodioActivoFijo
 * JD-Core Version:    0.7.0.1
 */