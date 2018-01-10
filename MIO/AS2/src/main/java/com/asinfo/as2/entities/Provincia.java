/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="provincia")
/*  24:    */ public class Provincia
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="provincia", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="provincia")
/*  32:    */   @Column(name="id_provincia")
/*  33:    */   private int idProvincia;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_pais", nullable=true)
/*  40:    */   private Pais pais;
/*  41:    */   @Column(name="codigo", length=10, nullable=false)
/*  42:    */   @NotNull
/*  43:    */   @Size(min=2, max=10)
/*  44:    */   private String codigo;
/*  45:    */   @Column(name="nombre", length=50, nullable=false)
/*  46:    */   @NotNull
/*  47:    */   @Size(min=2, max=50)
/*  48:    */   private String nombre;
/*  49:    */   @Column(name="descripcion", length=200)
/*  50:    */   @Size(max=200)
/*  51:    */   private String descripcion;
/*  52:    */   @Column(name="activo", nullable=false)
/*  53:    */   private boolean activo;
/*  54:    */   @Column(name="predeterminado", nullable=false)
/*  55:    */   private boolean predeterminado;
/*  56:    */   @Column(name="latitud", nullable=true, precision=12, scale=2)
/*  57:    */   @Digits(integer=2, fraction=6)
/*  58:    */   private BigDecimal latitud;
/*  59:    */   @Column(name="longitud", nullable=true, precision=12, scale=2)
/*  60:    */   @Digits(integer=3, fraction=6)
/*  61:    */   private BigDecimal longitud;
/*  62:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="provincia")
/*  63: 90 */   private List<Ciudad> listaCiudad = new ArrayList();
/*  64:    */   
/*  65:    */   public Provincia() {}
/*  66:    */   
/*  67:    */   public Provincia(int idProvincia, String codigo, String nombre)
/*  68:    */   {
/*  69:102 */     this.idProvincia = idProvincia;
/*  70:103 */     this.codigo = codigo;
/*  71:104 */     this.nombre = nombre;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getId()
/*  75:    */   {
/*  76:114 */     return getIdProvincia();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdProvincia()
/*  80:    */   {
/*  81:118 */     return this.idProvincia;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdProvincia(int idProvincia)
/*  85:    */   {
/*  86:122 */     this.idProvincia = idProvincia;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdOrganizacion()
/*  90:    */   {
/*  91:126 */     return this.idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdOrganizacion(int idOrganizacion)
/*  95:    */   {
/*  96:130 */     this.idOrganizacion = idOrganizacion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdSucursal()
/* 100:    */   {
/* 101:134 */     return this.idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdSucursal(int idSucursal)
/* 105:    */   {
/* 106:138 */     this.idSucursal = idSucursal;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Pais getPais()
/* 110:    */   {
/* 111:142 */     if (this.pais == null) {
/* 112:143 */       this.pais = new Pais();
/* 113:    */     }
/* 114:145 */     return this.pais;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setPais(Pais pais)
/* 118:    */   {
/* 119:149 */     this.pais = pais;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getCodigo()
/* 123:    */   {
/* 124:153 */     return this.codigo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setCodigo(String codigo)
/* 128:    */   {
/* 129:157 */     this.codigo = codigo;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getNombre()
/* 133:    */   {
/* 134:161 */     return this.nombre;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setNombre(String nombre)
/* 138:    */   {
/* 139:165 */     this.nombre = nombre;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getDescripcion()
/* 143:    */   {
/* 144:169 */     return this.descripcion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDescripcion(String descripcion)
/* 148:    */   {
/* 149:173 */     this.descripcion = descripcion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isActivo()
/* 153:    */   {
/* 154:177 */     return this.activo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setActivo(boolean activo)
/* 158:    */   {
/* 159:181 */     this.activo = activo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<Ciudad> getListaCiudad()
/* 163:    */   {
/* 164:190 */     if (this.listaCiudad == null) {
/* 165:191 */       this.listaCiudad = new ArrayList();
/* 166:    */     }
/* 167:193 */     return this.listaCiudad;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaCiudad(List<Ciudad> listaCiudad)
/* 171:    */   {
/* 172:203 */     this.listaCiudad = listaCiudad;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<String> getCamposAuditables()
/* 176:    */   {
/* 177:207 */     List<String> lista = new ArrayList();
/* 178:208 */     lista.add("codigo");
/* 179:209 */     lista.add("nombre");
/* 180:210 */     lista.add("descripcion");
/* 181:211 */     lista.add("activo");
/* 182:212 */     return lista;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String toString()
/* 186:    */   {
/* 187:217 */     return this.nombre;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isPredeterminado()
/* 191:    */   {
/* 192:224 */     return this.predeterminado;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setPredeterminado(boolean predeterminado)
/* 196:    */   {
/* 197:231 */     this.predeterminado = predeterminado;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Provincia clone()
/* 201:    */   {
/* 202:237 */     Provincia prov = new Provincia();
/* 203:238 */     prov.setActivo(this.activo);
/* 204:239 */     prov.setCodigo(this.codigo);
/* 205:240 */     prov.setDescripcion(this.descripcion);
/* 206:241 */     prov.setEliminado(this.eliminado);
/* 207:242 */     prov.setIdOrganizacion(this.idOrganizacion);
/* 208:243 */     prov.setIdProvincia(this.idProvincia);
/* 209:244 */     prov.setIdSucursal(this.idSucursal);
/* 210:245 */     prov.setListaCiudad(this.listaCiudad);
/* 211:246 */     prov.setNombre(this.nombre);
/* 212:247 */     prov.setPais(this.pais);
/* 213:248 */     prov.setPredeterminado(this.predeterminado);
/* 214:249 */     prov.setSoloLectura(isSoloLectura());
/* 215:250 */     prov.setLatitud(this.latitud);
/* 216:251 */     prov.setLongitud(this.longitud);
/* 217:252 */     return prov;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public BigDecimal getLatitud()
/* 221:    */   {
/* 222:256 */     return this.latitud;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setLatitud(BigDecimal latitud)
/* 226:    */   {
/* 227:260 */     this.latitud = latitud;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public BigDecimal getLongitud()
/* 231:    */   {
/* 232:264 */     return this.longitud;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setLongitud(BigDecimal longitud)
/* 236:    */   {
/* 237:268 */     this.longitud = longitud;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Provincia
 * JD-Core Version:    0.7.0.1
 */