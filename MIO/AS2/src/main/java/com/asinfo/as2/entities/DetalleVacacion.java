/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.PrePersist;
/*  16:    */ import javax.persistence.PreUpdate;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Temporal;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ import org.hibernate.annotations.ColumnDefault;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="detalle_vacacion")
/*  28:    */ public class DetalleVacacion
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 5991726820064379300L;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Id
/*  37:    */   @TableGenerator(name="detalle_vacacion", initialValue=0, allocationSize=50)
/*  38:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_vacacion")
/*  39:    */   @Column(name="id_detalle_vacacion")
/*  40:    */   private int idDetalleVacacion;
/*  41:    */   @Column(name="fecha_inicio", nullable=false)
/*  42:    */   @Temporal(TemporalType.DATE)
/*  43:    */   private Date fechaInicio;
/*  44:    */   @Column(name="fecha_fin", nullable=false)
/*  45:    */   @Temporal(TemporalType.DATE)
/*  46:    */   private Date fechaFin;
/*  47:    */   @Column(name="dias_tomados", nullable=false)
/*  48:    */   @Min(1L)
/*  49:    */   private int diasTomados;
/*  50:    */   @Column(name="dias_tomados_old", nullable=false)
/*  51:    */   @ColumnDefault("0")
/*  52:    */   @NotNull
/*  53:    */   private int diasTomadosOld;
/*  54:    */   @Column(name="estado", nullable=false)
/*  55:    */   @Enumerated(EnumType.ORDINAL)
/*  56:    */   private Estado estado;
/*  57:    */   @Column(name="descripcion", nullable=true, length=500)
/*  58:    */   @Size(max=500)
/*  59:    */   private String descripcion;
/*  60:    */   @Column(name="numero", nullable=false, length=20)
/*  61:    */   @NotNull
/*  62:    */   @Size(max=20)
/*  63:    */   @ColumnDefault("''")
/*  64:    */   private String numero;
/*  65:    */   @Column(name="vacacion_pagada", nullable=false)
/*  66:    */   @ColumnDefault("'0'")
/*  67:    */   @NotNull
/*  68:    */   private boolean vacacionPagada;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_vacacion", nullable=true)
/*  71:    */   private Vacacion vacacion;
/*  72:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  73:    */   @JoinColumn(name="id_documento", nullable=true)
/*  74:    */   @NotNull
/*  75:    */   private Documento documento;
/*  76:    */   @Column(name="aprobado_por", nullable=true, length=50)
/*  77:    */   private String aprobadoPor;
/*  78:    */   
/*  79:    */   public int getIdOrganizacion()
/*  80:    */   {
/*  81:147 */     return this.idOrganizacion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdOrganizacion(int idOrganizacion)
/*  85:    */   {
/*  86:157 */     this.idOrganizacion = idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdSucursal()
/*  90:    */   {
/*  91:166 */     return this.idSucursal;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdSucursal(int idSucursal)
/*  95:    */   {
/*  96:176 */     this.idSucursal = idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdDetalleVacacion()
/* 100:    */   {
/* 101:185 */     return this.idDetalleVacacion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdDetalleVacacion(int idDetalleVacacion)
/* 105:    */   {
/* 106:195 */     this.idDetalleVacacion = idDetalleVacacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Date getFechaInicio()
/* 110:    */   {
/* 111:204 */     return this.fechaInicio;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setFechaInicio(Date fechaInicio)
/* 115:    */   {
/* 116:214 */     this.fechaInicio = fechaInicio;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Date getFechaFin()
/* 120:    */   {
/* 121:223 */     return this.fechaFin;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setFechaFin(Date fechaFin)
/* 125:    */   {
/* 126:233 */     this.fechaFin = fechaFin;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getDiasTomados()
/* 130:    */   {
/* 131:242 */     return this.diasTomados;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setDiasTomados(int diasTomados)
/* 135:    */   {
/* 136:252 */     this.diasTomados = diasTomados;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getId()
/* 140:    */   {
/* 141:262 */     return this.idDetalleVacacion;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Vacacion getVacacion()
/* 145:    */   {
/* 146:271 */     return this.vacacion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setVacacion(Vacacion vacacion)
/* 150:    */   {
/* 151:281 */     this.vacacion = vacacion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Estado getEstado()
/* 155:    */   {
/* 156:290 */     return this.estado;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setEstado(Estado estado)
/* 160:    */   {
/* 161:300 */     this.estado = estado;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String getDescripcion()
/* 165:    */   {
/* 166:304 */     return this.descripcion;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setDescripcion(String descripcion)
/* 170:    */   {
/* 171:308 */     this.descripcion = descripcion;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getNumero()
/* 175:    */   {
/* 176:312 */     return this.numero;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setNumero(String numero)
/* 180:    */   {
/* 181:316 */     this.numero = numero;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Documento getDocumento()
/* 185:    */   {
/* 186:320 */     return this.documento;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setDocumento(Documento documento)
/* 190:    */   {
/* 191:324 */     this.documento = documento;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public int getDiasTomadosOld()
/* 195:    */   {
/* 196:328 */     return this.diasTomadosOld;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setDiasTomadosOld(int diasTomadosOld)
/* 200:    */   {
/* 201:332 */     this.diasTomadosOld = diasTomadosOld;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isVacacionPagada()
/* 205:    */   {
/* 206:336 */     return this.vacacionPagada;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setVacacionPagada(boolean vacacionPagada)
/* 210:    */   {
/* 211:340 */     this.vacacionPagada = vacacionPagada;
/* 212:    */   }
/* 213:    */   
/* 214:    */   @PrePersist
/* 215:    */   @PreUpdate
/* 216:    */   public void actualizarDatos()
/* 217:    */   {
/* 218:346 */     setDiasTomadosOld(getDiasTomados());
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String getAprobadoPor()
/* 222:    */   {
/* 223:353 */     return this.aprobadoPor;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setAprobadoPor(String aprobadoPor)
/* 227:    */   {
/* 228:360 */     this.aprobadoPor = aprobadoPor;
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleVacacion
 * JD-Core Version:    0.7.0.1
 */