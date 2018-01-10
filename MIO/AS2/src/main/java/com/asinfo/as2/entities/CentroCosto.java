/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.DiscriminatorColumn;
/*   8:    */ import javax.persistence.DiscriminatorType;
/*   9:    */ import javax.persistence.DiscriminatorValue;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.Inheritance;
/*  16:    */ import javax.persistence.InheritanceType;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ import javax.validation.constraints.Size;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
/*  26:    */ @DiscriminatorColumn(name="numero", discriminatorType=DiscriminatorType.STRING, length=1)
/*  27:    */ @DiscriminatorValue("1")
/*  28:    */ public class CentroCosto
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 8099197293018127192L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="centro_costo", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="centro_costo")
/*  36:    */   @Column(name="id_centro_costo")
/*  37:    */   private int idCentroCosto;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_nivel_centro_costo", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private NivelCentroCosto nivelCentroCosto;
/*  42:    */   @Column(name="id_organizacion")
/*  43:    */   private int idOrganizacion;
/*  44:    */   @Column(name="id_sucursal")
/*  45:    */   private int idSucursal;
/*  46:    */   @Column(name="codigo", nullable=false, length=20)
/*  47:    */   @NotNull
/*  48:    */   @Size(min=1, max=20)
/*  49:    */   private String codigo;
/*  50:    */   @Column(name="nombre", nullable=false, length=100)
/*  51:    */   @NotNull
/*  52:    */   @Size(min=2, max=100)
/*  53:    */   private String nombre;
/*  54:    */   @Column(name="descripcion", length=200, nullable=true)
/*  55:    */   @Size(max=200)
/*  56:    */   private String descripcion;
/*  57:    */   @Column(name="indicador_movimiento", nullable=false)
/*  58:    */   private boolean indicadorMovimiento;
/*  59:    */   @Column(name="predeterminado", nullable=false)
/*  60:    */   private boolean predeterminado;
/*  61:    */   @Column(name="activo", nullable=false)
/*  62:    */   private boolean activo;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_centro_costo_padre", nullable=true)
/*  65:    */   private CentroCosto centroCostoPadre;
/*  66:    */   @Transient
/*  67:    */   private String traMascara;
/*  68:    */   @Transient
/*  69:    */   private String traNombreNivelCentroCosto;
/*  70:    */   @Transient
/*  71:    */   private String traNombreParaMostrar;
/*  72:    */   
/*  73:    */   public CentroCosto() {}
/*  74:    */   
/*  75:    */   public CentroCosto(int idCentroCosto)
/*  76:    */   {
/*  77:111 */     this.idCentroCosto = idCentroCosto;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public CentroCosto(int idCentroCosto, String codigo, String nombre)
/*  81:    */   {
/*  82:121 */     this.idCentroCosto = idCentroCosto;
/*  83:122 */     this.codigo = codigo;
/*  84:123 */     this.nombre = nombre;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getId()
/*  88:    */   {
/*  89:133 */     return this.idCentroCosto;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdCentroCosto()
/*  93:    */   {
/*  94:143 */     return this.idCentroCosto;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdCentroCosto(int idCentroCosto)
/*  98:    */   {
/*  99:153 */     this.idCentroCosto = idCentroCosto;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public NivelCentroCosto getNivelCentroCosto()
/* 103:    */   {
/* 104:162 */     return this.nivelCentroCosto;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setNivelCentroCosto(NivelCentroCosto nivelCentroCosto)
/* 108:    */   {
/* 109:172 */     this.nivelCentroCosto = nivelCentroCosto;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getIdOrganizacion()
/* 113:    */   {
/* 114:181 */     return this.idOrganizacion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setIdOrganizacion(int idOrganizacion)
/* 118:    */   {
/* 119:191 */     this.idOrganizacion = idOrganizacion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getIdSucursal()
/* 123:    */   {
/* 124:200 */     return this.idSucursal;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setIdSucursal(int idSucursal)
/* 128:    */   {
/* 129:210 */     this.idSucursal = idSucursal;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getCodigo()
/* 133:    */   {
/* 134:219 */     return this.codigo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setCodigo(String codigo)
/* 138:    */   {
/* 139:229 */     this.codigo = codigo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getNombre()
/* 143:    */   {
/* 144:238 */     return this.nombre;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setNombre(String nombre)
/* 148:    */   {
/* 149:248 */     this.nombre = nombre;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getDescripcion()
/* 153:    */   {
/* 154:257 */     return this.descripcion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setDescripcion(String descripcion)
/* 158:    */   {
/* 159:267 */     this.descripcion = descripcion;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean isPredeterminado()
/* 163:    */   {
/* 164:276 */     return this.predeterminado;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setPredeterminado(boolean predeterminado)
/* 168:    */   {
/* 169:286 */     this.predeterminado = predeterminado;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean isActivo()
/* 173:    */   {
/* 174:295 */     return this.activo;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setActivo(boolean activo)
/* 178:    */   {
/* 179:305 */     this.activo = activo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String getTraMascara()
/* 183:    */   {
/* 184:314 */     return this.traMascara;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setTraMascara(String traMascara)
/* 188:    */   {
/* 189:324 */     this.traMascara = traMascara;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getTraNombreNivelCentroCosto()
/* 193:    */   {
/* 194:333 */     return this.traNombreNivelCentroCosto;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setTraNombreNivelCentroCosto(String traNombreNivelCentroCosto)
/* 198:    */   {
/* 199:343 */     this.traNombreNivelCentroCosto = traNombreNivelCentroCosto;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String getTraNombreParaMostrar()
/* 203:    */   {
/* 204:352 */     this.traNombreParaMostrar = (getCodigo() + " | " + getNombre());
/* 205:353 */     return this.traNombreParaMostrar;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setTraNombreParaMostrar(String traNombreParaMostrar)
/* 209:    */   {
/* 210:363 */     this.traNombreParaMostrar = traNombreParaMostrar;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public CentroCosto getCentroCostoPadre()
/* 214:    */   {
/* 215:372 */     return this.centroCostoPadre;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setCentroCostoPadre(CentroCosto centroCostoPadre)
/* 219:    */   {
/* 220:382 */     this.centroCostoPadre = centroCostoPadre;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean isIndicadorMovimiento()
/* 224:    */   {
/* 225:391 */     return this.indicadorMovimiento;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setIndicadorMovimiento(boolean indicadorMovimiento)
/* 229:    */   {
/* 230:401 */     this.indicadorMovimiento = indicadorMovimiento;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String toString()
/* 234:    */   {
/* 235:411 */     return this.nombre;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List<String> getCamposAuditables()
/* 239:    */   {
/* 240:421 */     List<String> lista = new ArrayList();
/* 241:422 */     lista.add("nivelCentroCosto");
/* 242:423 */     lista.add("codigo");
/* 243:424 */     lista.add("nombre");
/* 244:425 */     lista.add("descripcion");
/* 245:426 */     lista.add("predeterminado");
/* 246:427 */     lista.add("activo");
/* 247:    */     
/* 248:429 */     return lista;
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CentroCosto
 * JD-Core Version:    0.7.0.1
 */