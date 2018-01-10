/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.persistence.Temporal;
/*  13:    */ import javax.persistence.TemporalType;
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import javax.validation.constraints.Max;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="secuencia")
/*  22:    */ public class Secuencia
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="secuencia", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="secuencia")
/*  30:    */   @Column(name="id_secuencia")
/*  31:    */   private int idSecuencia;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal")
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="prefijo", nullable=false, length=8)
/*  41:    */   @NotNull
/*  42:    */   @Size(max=8)
/*  43:    */   private String prefijo;
/*  44:    */   @Column(name="sufijo", nullable=false, length=3)
/*  45:    */   @NotNull
/*  46:    */   @Size(max=3)
/*  47:    */   private String sufijo;
/*  48:    */   @Column(name="longitud", nullable=false)
/*  49:    */   @NotNull
/*  50:    */   @Min(1L)
/*  51:    */   @Max(20L)
/*  52:    */   private int longitud;
/*  53:    */   @Column(name="desde", nullable=false)
/*  54:    */   @NotNull
/*  55:    */   @Min(1L)
/*  56:    */   private int desde;
/*  57:    */   @Column(name="hasta", nullable=false)
/*  58:    */   @NotNull
/*  59:    */   @Min(1L)
/*  60:    */   private int hasta;
/*  61:    */   @Temporal(TemporalType.DATE)
/*  62:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  63:    */   @NotNull
/*  64:    */   private Date fechaDesde;
/*  65:    */   @Temporal(TemporalType.DATE)
/*  66:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  67:    */   @NotNull
/*  68:    */   private Date fechaHasta;
/*  69:    */   @Column(name="numero", nullable=false)
/*  70:    */   @NotNull
/*  71:    */   @Min(0L)
/*  72:    */   @Max(999999999L)
/*  73:    */   private int numero;
/*  74:    */   @Column(name="indicador_bloqueada")
/*  75:    */   private boolean indicadorBloqueada;
/*  76:    */   @Transient
/*  77:    */   private String patron;
/*  78:    */   
/*  79:    */   public Secuencia() {}
/*  80:    */   
/*  81:    */   public Secuencia(String prefijo, String sufijo, int numero)
/*  82:    */   {
/*  83:101 */     this.prefijo = prefijo;
/*  84:102 */     this.sufijo = sufijo;
/*  85:103 */     this.numero = numero;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Secuencia(int idSecuencia, String nombre)
/*  89:    */   {
/*  90:113 */     this.idSecuencia = idSecuencia;
/*  91:114 */     this.nombre = nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdSecuencia()
/*  95:    */   {
/*  96:124 */     return this.idSecuencia;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Secuencia(int desde, int hasta)
/* 100:    */   {
/* 101:133 */     this.desde = desde;
/* 102:134 */     this.hasta = hasta;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdSecuencia(int idSecuencia)
/* 106:    */   {
/* 107:144 */     this.idSecuencia = idSecuencia;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getIdOrganizacion()
/* 111:    */   {
/* 112:154 */     return this.idOrganizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setIdOrganizacion(int idOrganizacion)
/* 116:    */   {
/* 117:164 */     this.idOrganizacion = idOrganizacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getPrefijo()
/* 121:    */   {
/* 122:174 */     return this.prefijo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setPrefijo(String prefijo)
/* 126:    */   {
/* 127:185 */     this.prefijo = prefijo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getSufijo()
/* 131:    */   {
/* 132:195 */     return this.sufijo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setSufijo(String sufijo)
/* 136:    */   {
/* 137:205 */     this.sufijo = sufijo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getDesde()
/* 141:    */   {
/* 142:221 */     return this.desde;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDesde(int desde)
/* 146:    */   {
/* 147:231 */     this.desde = desde;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getHasta()
/* 151:    */   {
/* 152:241 */     return this.hasta;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setHasta(int hasta)
/* 156:    */   {
/* 157:251 */     this.hasta = hasta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Date getFechaDesde()
/* 161:    */   {
/* 162:261 */     return this.fechaDesde;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setFechaDesde(Date fechaDesde)
/* 166:    */   {
/* 167:271 */     this.fechaDesde = fechaDesde;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Date getFechaHasta()
/* 171:    */   {
/* 172:281 */     return this.fechaHasta;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setFechaHasta(Date fechaHasta)
/* 176:    */   {
/* 177:291 */     this.fechaHasta = fechaHasta;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getNumero()
/* 181:    */   {
/* 182:301 */     return this.numero;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setNumero(int numero)
/* 186:    */   {
/* 187:311 */     this.numero = numero;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getIdSucursal()
/* 191:    */   {
/* 192:321 */     return this.idSucursal;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setIdSucursal(int idSucursal)
/* 196:    */   {
/* 197:331 */     this.idSucursal = idSucursal;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public int getLongitud()
/* 201:    */   {
/* 202:341 */     return this.longitud;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setLongitud(int longitud)
/* 206:    */   {
/* 207:351 */     this.longitud = longitud;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getNombre()
/* 211:    */   {
/* 212:361 */     return this.nombre;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setNombre(String nombre)
/* 216:    */   {
/* 217:371 */     this.nombre = nombre;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String getPatron()
/* 221:    */   {
/* 222:375 */     if (getId() != 0)
/* 223:    */     {
/* 224:376 */       this.patron = this.prefijo;
/* 225:377 */       int numeroDigitos = this.longitud - this.prefijo.length() - this.sufijo.length();
/* 226:378 */       for (int i = 0; i < numeroDigitos; i++) {
/* 227:379 */         this.patron += "9";
/* 228:    */       }
/* 229:381 */       this.patron += this.sufijo;
/* 230:    */     }
/* 231:    */     else
/* 232:    */     {
/* 233:383 */       this.patron = "";
/* 234:    */     }
/* 235:385 */     return this.patron;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setPatron(String patron)
/* 239:    */   {
/* 240:389 */     this.patron = patron;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean isIndicadorBloqueada()
/* 244:    */   {
/* 245:393 */     return this.indicadorBloqueada;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setIndicadorBloqueada(boolean indicadorBloqueada)
/* 249:    */   {
/* 250:397 */     this.indicadorBloqueada = indicadorBloqueada;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public int getId()
/* 254:    */   {
/* 255:402 */     return this.idSecuencia;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String toString()
/* 259:    */   {
/* 260:407 */     return this.nombre;
/* 261:    */   }
/* 262:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Secuencia
 * JD-Core Version:    0.7.0.1
 */