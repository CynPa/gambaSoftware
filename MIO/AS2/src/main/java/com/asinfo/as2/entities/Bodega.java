/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.ClaseBodegaEnum;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Transient;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ import org.hibernate.annotations.ColumnDefault;
/*  25:    */ import org.hibernate.annotations.Fetch;
/*  26:    */ import org.hibernate.annotations.FetchMode;
/*  27:    */ 
/*  28:    */ @Entity
/*  29:    */ @Table(name="bodega", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  30:    */ public class Bodega
/*  31:    */   extends EntidadBase
/*  32:    */   implements Serializable
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @Id
/*  36:    */   @TableGenerator(name="bodega", initialValue=0, allocationSize=50)
/*  37:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="bodega")
/*  38:    */   @Column(name="id_bodega")
/*  39:    */   private int idBodega;
/*  40:    */   @Column(name="id_organizacion", nullable=false)
/*  41:    */   private int idOrganizacion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  44:    */   private Sucursal sucursal;
/*  45:    */   @Column(name="codigo", length=10, nullable=false)
/*  46:    */   @NotNull
/*  47:    */   @Size(min=2, max=10)
/*  48:    */   private String codigo;
/*  49:    */   @Column(name="nombre", length=50, nullable=false)
/*  50:    */   @NotNull
/*  51:    */   @Size(min=2, max=50)
/*  52:    */   private String nombre;
/*  53:    */   @Column(name="descripcion", length=200)
/*  54:    */   @Size(max=200)
/*  55:    */   private String descripcion;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="predeterminado", nullable=false)
/*  59:    */   private boolean predeterminado;
/*  60:    */   @NotNull
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_tipo_bodega", nullable=false)
/*  63:    */   private TipoBodega tipoBodega;
/*  64:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="bodega")
/*  65: 94 */   private List<UbicacionBodega> ubicacionesBodega = new ArrayList();
/*  66:    */   @OneToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_ubicacion", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   @Fetch(FetchMode.JOIN)
/*  70:    */   private Ubicacion ubicacion;
/*  71:    */   @Enumerated(EnumType.ORDINAL)
/*  72:    */   @Column(name="clase_bodega", nullable=false)
/*  73:    */   @NotNull
/*  74:    */   private ClaseBodegaEnum claseBodega;
/*  75:    */   @Column(name="indicador_recepcion_bodega", nullable=false)
/*  76:    */   @ColumnDefault("'0'")
/*  77:    */   public boolean indicadorRecepcionBodega;
/*  78:    */   @Transient
/*  79:    */   private Organizacion organizacion;
/*  80:    */   
/*  81:    */   public int getId()
/*  82:    */   {
/*  83:123 */     return this.idBodega;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Bodega() {}
/*  87:    */   
/*  88:    */   public Bodega(int idBodega)
/*  89:    */   {
/*  90:134 */     this.idBodega = idBodega;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Bodega(int idBodega, String codigo, String nombre)
/*  94:    */   {
/*  95:146 */     this.idBodega = idBodega;
/*  96:147 */     this.codigo = codigo;
/*  97:148 */     this.nombre = nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdBodega()
/* 101:    */   {
/* 102:157 */     return this.idBodega;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdBodega(int idBodega)
/* 106:    */   {
/* 107:167 */     this.idBodega = idBodega;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getCodigo()
/* 111:    */   {
/* 112:176 */     return this.codigo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setCodigo(String codigo)
/* 116:    */   {
/* 117:186 */     this.codigo = codigo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getNombre()
/* 121:    */   {
/* 122:195 */     return this.nombre;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setNombre(String nombre)
/* 126:    */   {
/* 127:205 */     this.nombre = nombre;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getDescripcion()
/* 131:    */   {
/* 132:214 */     return this.descripcion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setDescripcion(String descripcion)
/* 136:    */   {
/* 137:224 */     this.descripcion = descripcion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isActivo()
/* 141:    */   {
/* 142:233 */     return this.activo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setActivo(boolean activo)
/* 146:    */   {
/* 147:243 */     this.activo = activo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isPredeterminado()
/* 151:    */   {
/* 152:252 */     return this.predeterminado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPredeterminado(boolean predeterminado)
/* 156:    */   {
/* 157:262 */     this.predeterminado = predeterminado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public TipoBodega getTipoBodega()
/* 161:    */   {
/* 162:271 */     return this.tipoBodega;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTipoBodega(TipoBodega tipoBodega)
/* 166:    */   {
/* 167:281 */     this.tipoBodega = tipoBodega;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getIdOrganizacion()
/* 171:    */   {
/* 172:290 */     return this.idOrganizacion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setIdOrganizacion(int idOrganizacion)
/* 176:    */   {
/* 177:300 */     this.idOrganizacion = idOrganizacion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Sucursal getSucursal()
/* 181:    */   {
/* 182:309 */     return this.sucursal;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setSucursal(Sucursal sucursal)
/* 186:    */   {
/* 187:319 */     this.sucursal = sucursal;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String toString()
/* 191:    */   {
/* 192:329 */     return this.nombre;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<UbicacionBodega> getUbicacionesBodega()
/* 196:    */   {
/* 197:338 */     return this.ubicacionesBodega;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setUbicacionesBodega(List<UbicacionBodega> ubicacionesBodega)
/* 201:    */   {
/* 202:348 */     this.ubicacionesBodega = ubicacionesBodega;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Ubicacion getUbicacion()
/* 206:    */   {
/* 207:357 */     return this.ubicacion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setUbicacion(Ubicacion ubicacion)
/* 211:    */   {
/* 212:367 */     this.ubicacion = ubicacion;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<String> getCamposAuditables()
/* 216:    */   {
/* 217:371 */     ArrayList<String> lista = new ArrayList();
/* 218:372 */     lista.add("codigo");
/* 219:373 */     lista.add("tipoBodega");
/* 220:374 */     lista.add("nombre");
/* 221:375 */     lista.add("descripcion");
/* 222:376 */     lista.add("activo");
/* 223:    */     
/* 224:378 */     return lista;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public ClaseBodegaEnum getClaseBodega()
/* 228:    */   {
/* 229:387 */     return this.claseBodega;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setClaseBodega(ClaseBodegaEnum claseBodega)
/* 233:    */   {
/* 234:397 */     this.claseBodega = claseBodega;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Organizacion getOrganizacion()
/* 238:    */   {
/* 239:401 */     return this.organizacion;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setOrganizacion(Organizacion organizacion)
/* 243:    */   {
/* 244:405 */     this.organizacion = organizacion;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public boolean isIndicadorRecepcionBodega()
/* 248:    */   {
/* 249:409 */     return this.indicadorRecepcionBodega;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setIndicadorRecepcionBodega(boolean indicadorRecepcionBodega)
/* 253:    */   {
/* 254:413 */     this.indicadorRecepcionBodega = indicadorRecepcionBodega;
/* 255:    */   }
/* 256:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Bodega
 * JD-Core Version:    0.7.0.1
 */