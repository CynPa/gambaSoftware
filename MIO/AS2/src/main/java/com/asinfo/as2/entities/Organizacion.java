/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   4:    */ import java.io.Serializable;
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
/*  16:    */ import javax.persistence.OneToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ import javax.validation.constraints.Size;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="organizacion")
/*  25:    */ public class Organizacion
/*  26:    */   extends EntidadBase
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="organizacion", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="organizacion")
/*  33:    */   @Column(name="id_organizacion", unique=true, nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_tipo_identificacion")
/*  37:    */   private TipoIdentificacion tipoIdentificacion;
/*  38:    */   @Column(name="identificacion", nullable=false, length=20)
/*  39:    */   @Size(max=20)
/*  40:    */   @NotNull
/*  41:    */   private String identificacion;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_tipo_identificacion_contador")
/*  44:    */   private TipoIdentificacion tipoIdentificacionContador;
/*  45:    */   @Column(name="identificacion_representante", nullable=true, length=20)
/*  46:    */   @Size(max=20)
/*  47:    */   private String identificacionRepresentante;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_tipo_identificacion_representante")
/*  50:    */   private TipoIdentificacion tipoIdentificacionRepresentante;
/*  51:    */   @Column(name="representante_legal", length=50)
/*  52:    */   @Size(min=2, max=50)
/*  53:    */   private String representanteLegal;
/*  54:    */   @Column(name="identificacion_contador", nullable=true, length=20)
/*  55:    */   @Size(max=20)
/*  56:    */   private String identificacionContador;
/*  57:    */   @Column(name="razon_social", nullable=false, length=50)
/*  58:    */   @Size(min=2, max=50)
/*  59:    */   @NotNull
/*  60:    */   private String razonSocial;
/*  61:    */   @Column(name="pagina_web", length=200)
/*  62:    */   @Size(max=200)
/*  63:    */   private String paginaWeb;
/*  64:    */   @Column(name="imagen", nullable=true)
/*  65:    */   @Size(max=50)
/*  66:    */   private String imagen;
/*  67:    */   @OneToOne(mappedBy="organizacion", fetch=FetchType.LAZY)
/*  68:    */   private OrganizacionConfiguracion organizacionConfiguracion;
/*  69:    */   @OneToMany(mappedBy="organizacion", fetch=FetchType.LAZY)
/*  70: 86 */   private List<ProcesoOrganizacion> listaProcesoOrganizacion = new ArrayList();
/*  71:    */   @OneToMany(mappedBy="organizacion", fetch=FetchType.LAZY)
/*  72: 89 */   private List<UsuarioOrganizacion> listaUsuarioOrganizacion = new ArrayList();
/*  73:    */   @Transient
/*  74:    */   private String codigoOrganizacion;
/*  75:    */   @Transient
/*  76:    */   private boolean indicadorCopiar;
/*  77:    */   
/*  78:    */   public int getId()
/*  79:    */   {
/*  80:102 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdOrganizacion()
/*  84:    */   {
/*  85:111 */     return this.idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdOrganizacion(int idOrganizacion)
/*  89:    */   {
/*  90:121 */     this.idOrganizacion = idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public TipoIdentificacion getTipoIdentificacion()
/*  94:    */   {
/*  95:130 */     return this.tipoIdentificacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/*  99:    */   {
/* 100:140 */     this.tipoIdentificacion = tipoIdentificacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getIdentificacion()
/* 104:    */   {
/* 105:149 */     return this.identificacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdentificacion(String identificacion)
/* 109:    */   {
/* 110:159 */     this.identificacion = identificacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public TipoIdentificacion getTipoIdentificacionContador()
/* 114:    */   {
/* 115:168 */     return this.tipoIdentificacionContador;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setTipoIdentificacionContador(TipoIdentificacion tipoIdentificacionContador)
/* 119:    */   {
/* 120:178 */     this.tipoIdentificacionContador = tipoIdentificacionContador;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getIdentificacionRepresentante()
/* 124:    */   {
/* 125:187 */     return this.identificacionRepresentante;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdentificacionRepresentante(String identificacionRepresentante)
/* 129:    */   {
/* 130:197 */     this.identificacionRepresentante = identificacionRepresentante;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public TipoIdentificacion getTipoIdentificacionRepresentante()
/* 134:    */   {
/* 135:206 */     return this.tipoIdentificacionRepresentante;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setTipoIdentificacionRepresentante(TipoIdentificacion tipoIdentificacionRepresentante)
/* 139:    */   {
/* 140:216 */     this.tipoIdentificacionRepresentante = tipoIdentificacionRepresentante;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String getRepresentanteLegal()
/* 144:    */   {
/* 145:225 */     return this.representanteLegal;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setRepresentanteLegal(String representanteLegal)
/* 149:    */   {
/* 150:235 */     this.representanteLegal = representanteLegal;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getIdentificacionContador()
/* 154:    */   {
/* 155:244 */     return this.identificacionContador;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setIdentificacionContador(String identificacionContador)
/* 159:    */   {
/* 160:254 */     this.identificacionContador = identificacionContador;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getRazonSocial()
/* 164:    */   {
/* 165:263 */     return this.razonSocial;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setRazonSocial(String razonSocial)
/* 169:    */   {
/* 170:273 */     this.razonSocial = razonSocial;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String getPaginaWeb()
/* 174:    */   {
/* 175:282 */     return this.paginaWeb;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setPaginaWeb(String paginaWeb)
/* 179:    */   {
/* 180:292 */     this.paginaWeb = paginaWeb;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String toString()
/* 184:    */   {
/* 185:302 */     return this.razonSocial;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getImagen()
/* 189:    */   {
/* 190:311 */     return this.imagen;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setImagen(String imagen)
/* 194:    */   {
/* 195:321 */     this.imagen = imagen;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public OrganizacionConfiguracion getOrganizacionConfiguracion()
/* 199:    */   {
/* 200:330 */     return this.organizacionConfiguracion;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setOrganizacionConfiguracion(OrganizacionConfiguracion organizacionConfiguracion)
/* 204:    */   {
/* 205:340 */     this.organizacionConfiguracion = organizacionConfiguracion;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getCodigoOrganizacion()
/* 209:    */   {
/* 210:344 */     if (this.codigoOrganizacion == null) {
/* 211:345 */       this.codigoOrganizacion = FuncionesUtiles.completarALaIzquierda('0', 10, String.valueOf(getIdOrganizacion()));
/* 212:    */     }
/* 213:347 */     return this.codigoOrganizacion;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public List<ProcesoOrganizacion> getListaProcesoOrganizacion()
/* 217:    */   {
/* 218:351 */     return this.listaProcesoOrganizacion;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setListaProcesoOrganizacion(List<ProcesoOrganizacion> listaProcesoOrganizacion)
/* 222:    */   {
/* 223:356 */     this.listaProcesoOrganizacion = listaProcesoOrganizacion;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<UsuarioOrganizacion> getListaUsuarioOrganizacion()
/* 227:    */   {
/* 228:360 */     return this.listaUsuarioOrganizacion;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setListaUsuarioOrganizacion(List<UsuarioOrganizacion> listaUsuarioOrganizacion)
/* 232:    */   {
/* 233:365 */     this.listaUsuarioOrganizacion = listaUsuarioOrganizacion;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean isIndicadorCopiar()
/* 237:    */   {
/* 238:369 */     return this.indicadorCopiar;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setIndicadorCopiar(boolean indicadorCopiar)
/* 242:    */   {
/* 243:373 */     this.indicadorCopiar = indicadorCopiar;
/* 244:    */   }
/* 245:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Organizacion
 * JD-Core Version:    0.7.0.1
 */