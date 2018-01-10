/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoReporteAsiento;
/*   4:    */ import java.io.Serializable;
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
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="tipo_asiento")
/*  22:    */ public class TipoAsiento
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="tipo_asiento", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_asiento")
/*  30:    */   @Column(name="id_tipo_asiento")
/*  31:    */   private int idTipoAsiento;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_secuencia", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private Secuencia secuencia;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="id_sucursal", nullable=false)
/*  39:    */   private int idSucursal;
/*  40:    */   @Column(name="nombre", nullable=false, length=50)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=50)
/*  43:    */   private String nombre;
/*  44:    */   @Enumerated(EnumType.ORDINAL)
/*  45:    */   @Column(name="tipo_reporte_asiento", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private TipoReporteAsiento tipoReporteAsiento;
/*  48:    */   @Column(name="reporte", length=50, nullable=false)
/*  49:    */   @Size(min=2, max=50)
/*  50:    */   private String reporte;
/*  51:    */   @Column(name="reporte_resumen", length=50, nullable=true)
/*  52:    */   @Size(max=50)
/*  53:    */   private String reporteResumen;
/*  54:    */   @Column(name="indicador_NIIF", nullable=false)
/*  55:    */   private boolean indicadorNIIF;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="descripcion", length=200)
/*  59:    */   @Size(max=200)
/*  60:    */   private String descripcion;
/*  61:    */   @Column(name="predeterminado", nullable=false)
/*  62:    */   private boolean predeterminado;
/*  63:    */   
/*  64:    */   public TipoAsiento() {}
/*  65:    */   
/*  66:    */   public TipoAsiento(int idTipoAsiento, String nombre)
/*  67:    */   {
/*  68: 93 */     this.idTipoAsiento = idTipoAsiento;
/*  69: 94 */     this.nombre = nombre;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdTipoAsiento()
/*  73:    */   {
/*  74:104 */     return this.idTipoAsiento;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdTipoAsiento(int idTipoAsiento)
/*  78:    */   {
/*  79:114 */     this.idTipoAsiento = idTipoAsiento;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Secuencia getSecuencia()
/*  83:    */   {
/*  84:124 */     return this.secuencia;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setSecuencia(Secuencia secuencia)
/*  88:    */   {
/*  89:134 */     this.secuencia = secuencia;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdOrganizacion()
/*  93:    */   {
/*  94:144 */     return this.idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdOrganizacion(int idOrganizacion)
/*  98:    */   {
/*  99:154 */     this.idOrganizacion = idOrganizacion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getIdSucursal()
/* 103:    */   {
/* 104:164 */     return this.idSucursal;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIdSucursal(int idSucursal)
/* 108:    */   {
/* 109:174 */     this.idSucursal = idSucursal;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getNombre()
/* 113:    */   {
/* 114:184 */     return this.nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setNombre(String nombre)
/* 118:    */   {
/* 119:194 */     this.nombre = nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isActivo()
/* 123:    */   {
/* 124:204 */     return this.activo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setActivo(boolean activo)
/* 128:    */   {
/* 129:214 */     this.activo = activo;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getDescripcion()
/* 133:    */   {
/* 134:224 */     return this.descripcion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDescripcion(String descripcion)
/* 138:    */   {
/* 139:234 */     this.descripcion = descripcion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isPredeterminado()
/* 143:    */   {
/* 144:244 */     return this.predeterminado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setPredeterminado(boolean predeterminado)
/* 148:    */   {
/* 149:254 */     this.predeterminado = predeterminado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int getId()
/* 153:    */   {
/* 154:259 */     return this.idTipoAsiento;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String toString()
/* 158:    */   {
/* 159:264 */     return this.nombre;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean isIndicadorNIIF()
/* 163:    */   {
/* 164:273 */     return this.indicadorNIIF;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setIndicadorNIIF(boolean indicadorNIIF)
/* 168:    */   {
/* 169:283 */     this.indicadorNIIF = indicadorNIIF;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getReporte()
/* 173:    */   {
/* 174:292 */     return this.reporte;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setReporte(String reporte)
/* 178:    */   {
/* 179:302 */     this.reporte = reporte;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public TipoReporteAsiento getTipoReporteAsiento()
/* 183:    */   {
/* 184:311 */     return this.tipoReporteAsiento;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setTipoReporteAsiento(TipoReporteAsiento tipoReporteAsiento)
/* 188:    */   {
/* 189:321 */     this.tipoReporteAsiento = tipoReporteAsiento;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getReporteResumen()
/* 193:    */   {
/* 194:325 */     return this.reporteResumen;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setReporteResumen(String reporteResumen)
/* 198:    */   {
/* 199:329 */     this.reporteResumen = reporteResumen;
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoAsiento
 * JD-Core Version:    0.7.0.1
 */