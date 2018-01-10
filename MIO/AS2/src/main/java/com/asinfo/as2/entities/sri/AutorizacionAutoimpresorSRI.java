/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
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
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="autorizacion_autoimpresor_SRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "autorizacion"})})
/*  23:    */ public class AutorizacionAutoimpresorSRI
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="autorizacion_autoimpresor_SRI", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="autorizacion_autoimpresor_SRI")
/*  30:    */   @Column(name="id_autorizacion_autoimpresor_SRI")
/*  31:    */   private int idAutorizacionAutoimpresorSRI;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="autorizacion", length=10, nullable=false)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=10, max=37)
/*  39:    */   private String autorizacion;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_autorizacion_autoimpresor_SRI_anterior", nullable=true)
/*  42:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRIAnterior;
/*  43:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  44:    */   @NotNull
/*  45:    */   private Date fechaDesde;
/*  46:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  47:    */   @NotNull
/*  48:    */   private Date fechaHasta;
/*  49:    */   @Column(name="fecha_baja", nullable=true, length=23)
/*  50:    */   private Date fechaBaja;
/*  51:    */   @Column(name="fecha_cambio_software", nullable=true, length=23)
/*  52:    */   private Date fechaCambioSoftware;
/*  53:    */   @Column(name="activo", nullable=false)
/*  54:    */   private boolean activo;
/*  55:    */   @Column(name="predeterminado", nullable=false)
/*  56:    */   private boolean predeterminado;
/*  57:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="autorizacionAutoimpresorSRI")
/*  58: 88 */   private List<AutorizacionDocumentoAutoimpresorSRI> listaAutorizacionDocumentoAutoimpresorSRI = new ArrayList();
/*  59:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="autorizacionAutoimpresorSRI")
/*  60: 91 */   private List<AutorizacionPuntoDeVentaAutoimpresorSRI> listaAutorizacionPuntoDeVentaAutoimpresorSRI = new ArrayList();
/*  61:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="autorizacionAutoimpresorSRI")
/*  62: 94 */   private List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI = new ArrayList();
/*  63:    */   
/*  64:    */   public int getId()
/*  65:    */   {
/*  66: 99 */     return this.idAutorizacionAutoimpresorSRI;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String toString()
/*  70:    */   {
/*  71:104 */     return this.autorizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdAutorizacionAutoimpresorSRI()
/*  75:    */   {
/*  76:113 */     return this.idAutorizacionAutoimpresorSRI;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdAutorizacionAutoimpresorSRI(int idAutorizacionAutoimpresorSRI)
/*  80:    */   {
/*  81:123 */     this.idAutorizacionAutoimpresorSRI = idAutorizacionAutoimpresorSRI;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdOrganizacion()
/*  85:    */   {
/*  86:132 */     return this.idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdOrganizacion(int idOrganizacion)
/*  90:    */   {
/*  91:142 */     this.idOrganizacion = idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getIdSucursal()
/*  95:    */   {
/*  96:151 */     return this.idSucursal;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdSucursal(int idSucursal)
/* 100:    */   {
/* 101:161 */     this.idSucursal = idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getAutorizacion()
/* 105:    */   {
/* 106:170 */     return this.autorizacion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setAutorizacion(String autorizacion)
/* 110:    */   {
/* 111:180 */     this.autorizacion = autorizacion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:189 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:199 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isPredeterminado()
/* 125:    */   {
/* 126:208 */     return this.predeterminado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPredeterminado(boolean predeterminado)
/* 130:    */   {
/* 131:218 */     this.predeterminado = predeterminado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Date getFechaDesde()
/* 135:    */   {
/* 136:222 */     return this.fechaDesde;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setFechaDesde(Date fechaDesde)
/* 140:    */   {
/* 141:226 */     this.fechaDesde = fechaDesde;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Date getFechaHasta()
/* 145:    */   {
/* 146:230 */     return this.fechaHasta;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setFechaHasta(Date fechaHasta)
/* 150:    */   {
/* 151:234 */     this.fechaHasta = fechaHasta;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<AutorizacionDocumentoAutoimpresorSRI> getListaAutorizacionDocumentoAutoimpresorSRI()
/* 155:    */   {
/* 156:238 */     return this.listaAutorizacionDocumentoAutoimpresorSRI;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<AutorizacionDocumentoAutoimpresorSRI> getListaAutorizacionDocumentoAutoimpresorSRIActivos()
/* 160:    */   {
/* 161:242 */     List<AutorizacionDocumentoAutoimpresorSRI> lista = new ArrayList();
/* 162:243 */     for (AutorizacionDocumentoAutoimpresorSRI autorizacionDocumentoAutoimpresorSRI : this.listaAutorizacionDocumentoAutoimpresorSRI) {
/* 163:244 */       if (autorizacionDocumentoAutoimpresorSRI.isActivo()) {
/* 164:245 */         lista.add(autorizacionDocumentoAutoimpresorSRI);
/* 165:    */       }
/* 166:    */     }
/* 167:248 */     return lista;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaAutorizacionDocumentoAutoimpresorSRI(List<AutorizacionDocumentoAutoimpresorSRI> listaAutorizacionDocumentoAutoimpresorSRI)
/* 171:    */   {
/* 172:252 */     this.listaAutorizacionDocumentoAutoimpresorSRI = listaAutorizacionDocumentoAutoimpresorSRI;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<AutorizacionPuntoDeVentaAutoimpresorSRI> getListaAutorizacionPuntoDeVentaAutoimpresorSRI()
/* 176:    */   {
/* 177:256 */     return this.listaAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<AutorizacionPuntoDeVentaAutoimpresorSRI> getListaAutorizacionPuntoDeVentaAutoimpresorSRIActivos()
/* 181:    */   {
/* 182:260 */     List<AutorizacionPuntoDeVentaAutoimpresorSRI> lista = new ArrayList();
/* 183:261 */     for (AutorizacionPuntoDeVentaAutoimpresorSRI autorizacionPuntoDeVentaAutoimpresorSRI : this.listaAutorizacionPuntoDeVentaAutoimpresorSRI) {
/* 184:262 */       if (autorizacionPuntoDeVentaAutoimpresorSRI.isActivo()) {
/* 185:263 */         lista.add(autorizacionPuntoDeVentaAutoimpresorSRI);
/* 186:    */       }
/* 187:    */     }
/* 188:266 */     return lista;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setListaAutorizacionPuntoDeVentaAutoimpresorSRI(List<AutorizacionPuntoDeVentaAutoimpresorSRI> listaAutorizacionPuntoDeVentaAutoimpresorSRI)
/* 192:    */   {
/* 193:271 */     this.listaAutorizacionPuntoDeVentaAutoimpresorSRI = listaAutorizacionPuntoDeVentaAutoimpresorSRI;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI()
/* 197:    */   {
/* 198:275 */     return this.listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRIActivos()
/* 202:    */   {
/* 203:279 */     List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> lista = new ArrayList();
/* 204:280 */     for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI autorizacionDocumentoPuntoDeVentaAutoimpresorSRI : this.listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI) {
/* 205:281 */       if (autorizacionDocumentoPuntoDeVentaAutoimpresorSRI.isActivo()) {
/* 206:282 */         lista.add(autorizacionDocumentoPuntoDeVentaAutoimpresorSRI);
/* 207:    */       }
/* 208:    */     }
/* 209:285 */     return lista;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI(List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)
/* 213:    */   {
/* 214:290 */     this.listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI = listaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRIAnterior()
/* 218:    */   {
/* 219:294 */     return this.autorizacionAutoimpresorSRIAnterior;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setAutorizacionAutoimpresorSRIAnterior(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRIAnterior)
/* 223:    */   {
/* 224:298 */     this.autorizacionAutoimpresorSRIAnterior = autorizacionAutoimpresorSRIAnterior;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Date getFechaBaja()
/* 228:    */   {
/* 229:302 */     return this.fechaBaja;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setFechaBaja(Date fechaBaja)
/* 233:    */   {
/* 234:306 */     this.fechaBaja = fechaBaja;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Date getFechaCambioSoftware()
/* 238:    */   {
/* 239:310 */     return this.fechaCambioSoftware;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setFechaCambioSoftware(Date fechaCambioSoftware)
/* 243:    */   {
/* 244:314 */     this.fechaCambioSoftware = fechaCambioSoftware;
/* 245:    */   }
/* 246:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI
 * JD-Core Version:    0.7.0.1
 */