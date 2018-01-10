/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.validation.constraints.Max;
/*  20:    */ import javax.validation.constraints.Min;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="ejercicio")
/*  26:    */ public class Ejercicio
/*  27:    */   extends EntidadBase
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="ejercicio", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ejercicio")
/*  34:    */   @Column(name="id_ejercicio")
/*  35:    */   private int idEjercicio;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @Column(name="id_sucursal", nullable=false)
/*  43:    */   private int idSucursal;
/*  44:    */   @Temporal(TemporalType.DATE)
/*  45:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  46:    */   @NotNull
/*  47:    */   private Date fechaDesde;
/*  48:    */   @Temporal(TemporalType.DATE)
/*  49:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  50:    */   @NotNull
/*  51:    */   private Date fechaHasta;
/*  52:    */   @Column(name="descripcion", length=200, nullable=true)
/*  53:    */   @Size(max=200)
/*  54:    */   private String descripcion;
/*  55:    */   @Column(name="activo", nullable=false)
/*  56:    */   @NotNull
/*  57:    */   private boolean activo;
/*  58:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="ejercicio")
/*  59: 72 */   private List<Periodo> periodos = new ArrayList();
/*  60:    */   @Min(2000L)
/*  61:    */   @Max(9999L)
/*  62: 75 */   private transient Integer asignacionAnio = null;
/*  63:    */   
/*  64:    */   public int getIdEjercicio()
/*  65:    */   {
/*  66: 88 */     return this.idEjercicio;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdEjercicio(int idEjercicio)
/*  70:    */   {
/*  71: 98 */     this.idEjercicio = idEjercicio;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getDescripcion()
/*  75:    */   {
/*  76:107 */     return this.descripcion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setDescripcion(String descripcion)
/*  80:    */   {
/*  81:117 */     this.descripcion = descripcion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Date getFechaDesde()
/*  85:    */   {
/*  86:126 */     return this.fechaDesde;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setFechaDesde(Date fechaDesde)
/*  90:    */   {
/*  91:136 */     this.fechaDesde = fechaDesde;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<Periodo> getPeriodos()
/*  95:    */   {
/*  96:145 */     return this.periodos;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setPeriodos(List<Periodo> periodos)
/* 100:    */   {
/* 101:155 */     this.periodos = periodos;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Date getFechaHasta()
/* 105:    */   {
/* 106:164 */     return this.fechaHasta;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setFechaHasta(Date fechaHasta)
/* 110:    */   {
/* 111:174 */     this.fechaHasta = fechaHasta;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:183 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:193 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getIdOrganizacion()
/* 125:    */   {
/* 126:202 */     return this.idOrganizacion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setIdOrganizacion(int idOrganizacion)
/* 130:    */   {
/* 131:212 */     this.idOrganizacion = idOrganizacion;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getIdSucursal()
/* 135:    */   {
/* 136:221 */     return this.idSucursal;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setIdSucursal(int idSucursal)
/* 140:    */   {
/* 141:231 */     this.idSucursal = idSucursal;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getNombre()
/* 145:    */   {
/* 146:240 */     return this.nombre;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setNombre(String nombre)
/* 150:    */   {
/* 151:250 */     this.nombre = nombre;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getId()
/* 155:    */   {
/* 156:261 */     return getIdEjercicio();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String toString()
/* 160:    */   {
/* 161:266 */     return this.nombre;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Integer getAsignacionAnio()
/* 165:    */   {
/* 166:270 */     return this.asignacionAnio;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setAsignacionAnio(Integer asignacionAnio)
/* 170:    */   {
/* 171:274 */     this.asignacionAnio = asignacionAnio;
/* 172:275 */     if (this.asignacionAnio != null)
/* 173:    */     {
/* 174:276 */       setFechaDesde(FuncionesUtiles.getFecha(1, 1, this.asignacionAnio.intValue()));
/* 175:277 */       setFechaHasta(FuncionesUtiles.getFecha(31, 12, this.asignacionAnio.intValue()));
/* 176:    */     }
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Ejercicio
 * JD-Core Version:    0.7.0.1
 */