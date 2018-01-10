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
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ import org.hibernate.annotations.ColumnDefault;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="detalle_permiso_empleado")
/*  25:    */ public class DetallePermisoEmpleado
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="detalle_permiso_empleado", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_permiso_empleado")
/*  36:    */   @Column(name="id_detalle_permiso_empleado")
/*  37:    */   private int idDetallePermisoEmpleado;
/*  38:    */   @Column(name="fecha_permiso", nullable=false)
/*  39:    */   @Temporal(TemporalType.DATE)
/*  40:    */   @NotNull
/*  41:    */   private Date fechaPermiso;
/*  42:    */   @Column(name="horas", nullable=false, precision=12, scale=2)
/*  43:    */   @Min(0L)
/*  44:    */   @Digits(integer=12, fraction=2)
/*  45: 61 */   private BigDecimal horas = BigDecimal.ZERO;
/*  46:    */   @Temporal(TemporalType.TIME)
/*  47:    */   @Column(name="hora_desde", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Date horaDesde;
/*  50:    */   @Temporal(TemporalType.TIME)
/*  51:    */   @Column(name="hora_hasta", nullable=true)
/*  52:    */   private Date horaHasta;
/*  53:    */   @Column(name="descripcion", length=200, nullable=true)
/*  54:    */   @Size(max=200)
/*  55:    */   private String descripcion;
/*  56:    */   @Column(name="indicador_dia_completo", nullable=false)
/*  57:    */   @ColumnDefault("'0'")
/*  58:    */   @NotNull
/*  59:    */   private boolean indicadorDiaCompleto;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_permiso_empleado", nullable=true)
/*  62:    */   private PermisoEmpleado permisoEmpleado;
/*  63:    */   
/*  64:    */   public int getId()
/*  65:    */   {
/*  66: 98 */     return this.idDetallePermisoEmpleado;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdOrganizacion()
/*  70:    */   {
/*  71:102 */     return this.idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdOrganizacion(int idOrganizacion)
/*  75:    */   {
/*  76:106 */     this.idOrganizacion = idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdSucursal()
/*  80:    */   {
/*  81:110 */     return this.idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdSucursal(int idSucursal)
/*  85:    */   {
/*  86:114 */     this.idSucursal = idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdDetallePermisoEmpleado()
/*  90:    */   {
/*  91:118 */     return this.idDetallePermisoEmpleado;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdDetallePermisoEmpleado(int idDetallePermisoEmpleado)
/*  95:    */   {
/*  96:122 */     this.idDetallePermisoEmpleado = idDetallePermisoEmpleado;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Date getFechaPermiso()
/* 100:    */   {
/* 101:126 */     return this.fechaPermiso;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setFechaPermiso(Date fechaPermiso)
/* 105:    */   {
/* 106:130 */     this.fechaPermiso = fechaPermiso;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public BigDecimal getHoras()
/* 110:    */   {
/* 111:134 */     return this.horas;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setHoras(BigDecimal horas)
/* 115:    */   {
/* 116:138 */     this.horas = horas;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Date getHoraDesde()
/* 120:    */   {
/* 121:142 */     return this.horaDesde;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setHoraDesde(Date horaDesde)
/* 125:    */   {
/* 126:146 */     this.horaDesde = horaDesde;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Date getHoraHasta()
/* 130:    */   {
/* 131:150 */     return this.horaHasta;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setHoraHasta(Date horaHasta)
/* 135:    */   {
/* 136:154 */     this.horaHasta = horaHasta;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getDescripcion()
/* 140:    */   {
/* 141:158 */     return this.descripcion;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setDescripcion(String descripcion)
/* 145:    */   {
/* 146:162 */     this.descripcion = descripcion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public PermisoEmpleado getPermisoEmpleado()
/* 150:    */   {
/* 151:166 */     return this.permisoEmpleado;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setPermisoEmpleado(PermisoEmpleado permisoEmpleado)
/* 155:    */   {
/* 156:170 */     this.permisoEmpleado = permisoEmpleado;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isIndicadorDiaCompleto()
/* 160:    */   {
/* 161:174 */     return this.indicadorDiaCompleto;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setIndicadorDiaCompleto(boolean indicadorDiaCompleto)
/* 165:    */   {
/* 166:178 */     this.indicadorDiaCompleto = indicadorDiaCompleto;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallePermisoEmpleado
 * JD-Core Version:    0.7.0.1
 */