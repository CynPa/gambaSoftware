/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoComponenteCostoEnum;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="componente_costo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  23:    */ public class ComponenteCosto
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -3882890584053192779L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="componente_costo", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="componente_costo")
/*  30:    */   @Column(name="id_componente_costo")
/*  31:    */   private int idComponenteCosto;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="codigo", nullable=false, length=10)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=10)
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="nombre", nullable=false, length=50)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=2, max=50)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Enumerated(EnumType.ORDINAL)
/*  52:    */   @Column(name="tipo_componente_costo", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   private TipoComponenteCostoEnum tipoComponenteCostoEnum;
/*  55:    */   @Column(name="indicador_prorratear_horas_hombre")
/*  56: 88 */   private Boolean indicadorProrratearHorasHombre = Boolean.valueOf(false);
/*  57:    */   @Column(name="indicador_prorratear_horas_hombre_x_valor")
/*  58: 91 */   private Boolean indicadorProrratearHorasHombreXValor = Boolean.valueOf(false);
/*  59:    */   @Column(name="indicador_prorratear_horas_maquina")
/*  60: 94 */   private Boolean indicadorProrratearHorasMaquina = Boolean.valueOf(false);
/*  61:    */   @Column(name="indicador_prorratear_horas_maquina_x_valor")
/*  62: 97 */   private Boolean indicadorProrratearHorasMaquinaXValor = Boolean.valueOf(false);
/*  63:    */   @Column(name="indicador_coeficiente_produccion")
/*  64:    */   @ColumnDefault("'0'")
/*  65:    */   @NotNull
/*  66: 99 */   private boolean indicadorCoeficienteProduccion = false;
/*  67:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="componenteCosto")
/*  68:104 */   private List<DetalleComponenteCosto> listaDetalleComponenteCosto = new ArrayList();
/*  69:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="componenteCosto")
/*  70:107 */   private List<DetalleComponenteCostoDistribucion> listaDetalleComponenteCostoDistribucion = new ArrayList();
/*  71:    */   
/*  72:    */   public ComponenteCosto() {}
/*  73:    */   
/*  74:    */   public ComponenteCosto(int idComponenteCosto, String nombre)
/*  75:    */   {
/*  76:119 */     this.idComponenteCosto = idComponenteCosto;
/*  77:120 */     this.nombre = nombre;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdComponenteCosto()
/*  81:    */   {
/*  82:129 */     return this.idComponenteCosto;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdComponenteCosto(int idComponenteCosto)
/*  86:    */   {
/*  87:139 */     this.idComponenteCosto = idComponenteCosto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdOrganizacion()
/*  91:    */   {
/*  92:148 */     return this.idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdOrganizacion(int idOrganizacion)
/*  96:    */   {
/*  97:158 */     this.idOrganizacion = idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdSucursal()
/* 101:    */   {
/* 102:167 */     return this.idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdSucursal(int idSucursal)
/* 106:    */   {
/* 107:177 */     this.idSucursal = idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getNombre()
/* 111:    */   {
/* 112:186 */     return this.nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setNombre(String nombre)
/* 116:    */   {
/* 117:196 */     this.nombre = nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getDescripcion()
/* 121:    */   {
/* 122:205 */     return this.descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDescripcion(String descripcion)
/* 126:    */   {
/* 127:215 */     this.descripcion = descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isActivo()
/* 131:    */   {
/* 132:224 */     return this.activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setActivo(boolean activo)
/* 136:    */   {
/* 137:234 */     this.activo = activo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isPredeterminado()
/* 141:    */   {
/* 142:243 */     return this.predeterminado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setPredeterminado(boolean predeterminado)
/* 146:    */   {
/* 147:253 */     this.predeterminado = predeterminado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getCodigo()
/* 151:    */   {
/* 152:262 */     return this.codigo;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setCodigo(String codigo)
/* 156:    */   {
/* 157:272 */     this.codigo = codigo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public TipoComponenteCostoEnum getTipoComponenteCostoEnum()
/* 161:    */   {
/* 162:281 */     return this.tipoComponenteCostoEnum;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTipoComponenteCostoEnum(TipoComponenteCostoEnum tipoComponenteCostoEnum)
/* 166:    */   {
/* 167:291 */     this.tipoComponenteCostoEnum = tipoComponenteCostoEnum;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getId()
/* 171:    */   {
/* 172:296 */     return this.idComponenteCosto;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Boolean getIndicadorProrratearHorasHombre()
/* 176:    */   {
/* 177:300 */     if (this.indicadorProrratearHorasHombre == null) {
/* 178:301 */       this.indicadorProrratearHorasHombre = Boolean.valueOf(false);
/* 179:    */     }
/* 180:303 */     return this.indicadorProrratearHorasHombre;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIndicadorProrratearHorasHombre(Boolean indicadorProrratearHorasHombre)
/* 184:    */   {
/* 185:307 */     if (indicadorProrratearHorasHombre == null) {
/* 186:308 */       indicadorProrratearHorasHombre = Boolean.valueOf(false);
/* 187:    */     }
/* 188:310 */     this.indicadorProrratearHorasHombre = indicadorProrratearHorasHombre;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Boolean getIndicadorProrratearHorasHombreXValor()
/* 192:    */   {
/* 193:314 */     if (this.indicadorProrratearHorasHombreXValor == null) {
/* 194:315 */       this.indicadorProrratearHorasHombreXValor = Boolean.valueOf(false);
/* 195:    */     }
/* 196:317 */     return this.indicadorProrratearHorasHombreXValor;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setIndicadorProrratearHorasHombreXValor(Boolean indicadorProrratearHorasHombreXValor)
/* 200:    */   {
/* 201:321 */     if (indicadorProrratearHorasHombreXValor == null) {
/* 202:322 */       indicadorProrratearHorasHombreXValor = Boolean.valueOf(false);
/* 203:    */     }
/* 204:324 */     this.indicadorProrratearHorasHombreXValor = indicadorProrratearHorasHombreXValor;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Boolean getIndicadorProrratearHorasMaquina()
/* 208:    */   {
/* 209:328 */     if (this.indicadorProrratearHorasMaquina == null) {
/* 210:329 */       this.indicadorProrratearHorasMaquina = Boolean.valueOf(false);
/* 211:    */     }
/* 212:331 */     return this.indicadorProrratearHorasMaquina;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setIndicadorProrratearHorasMaquina(Boolean indicadorProrratearHorasMaquina)
/* 216:    */   {
/* 217:335 */     if (indicadorProrratearHorasMaquina == null) {
/* 218:336 */       indicadorProrratearHorasMaquina = Boolean.valueOf(false);
/* 219:    */     }
/* 220:338 */     this.indicadorProrratearHorasMaquina = indicadorProrratearHorasMaquina;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Boolean getIndicadorProrratearHorasMaquinaXValor()
/* 224:    */   {
/* 225:342 */     if (this.indicadorProrratearHorasMaquinaXValor == null) {
/* 226:343 */       this.indicadorProrratearHorasMaquinaXValor = Boolean.valueOf(false);
/* 227:    */     }
/* 228:345 */     return this.indicadorProrratearHorasMaquinaXValor;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setIndicadorProrratearHorasMaquinaXValor(Boolean indicadorProrratearHorasMaquinaXValor)
/* 232:    */   {
/* 233:349 */     if (indicadorProrratearHorasMaquinaXValor == null) {
/* 234:350 */       indicadorProrratearHorasMaquinaXValor = Boolean.valueOf(false);
/* 235:    */     }
/* 236:352 */     this.indicadorProrratearHorasMaquinaXValor = indicadorProrratearHorasMaquinaXValor;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<DetalleComponenteCosto> getListaDetalleComponenteCosto()
/* 240:    */   {
/* 241:356 */     return this.listaDetalleComponenteCosto;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setListaDetalleComponenteCosto(List<DetalleComponenteCosto> listaDetalleComponenteCosto)
/* 245:    */   {
/* 246:360 */     this.listaDetalleComponenteCosto = listaDetalleComponenteCosto;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public List<DetalleComponenteCostoDistribucion> getListaDetalleComponenteCostoDistribucion()
/* 250:    */   {
/* 251:364 */     return this.listaDetalleComponenteCostoDistribucion;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaDetalleComponenteCostoDistribucion(List<DetalleComponenteCostoDistribucion> listaDetalleComponenteCostoDistribucion)
/* 255:    */   {
/* 256:368 */     this.listaDetalleComponenteCostoDistribucion = listaDetalleComponenteCostoDistribucion;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Boolean getIndicadorCoeficienteProduccion()
/* 260:    */   {
/* 261:372 */     return Boolean.valueOf(this.indicadorCoeficienteProduccion);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setIndicadorCoeficienteProduccion(Boolean indicadorCoeficienteProduccion)
/* 265:    */   {
/* 266:376 */     this.indicadorCoeficienteProduccion = indicadorCoeficienteProduccion.booleanValue();
/* 267:    */   }
/* 268:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ComponenteCosto
 * JD-Core Version:    0.7.0.1
 */