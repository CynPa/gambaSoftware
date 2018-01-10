/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.dao.BodegaDao;
/*   5:    */ import com.asinfo.as2.dao.CajaDao;
/*   6:    */ import com.asinfo.as2.dao.CategoriaEmpresaDao;
/*   7:    */ import com.asinfo.as2.dao.CategoriaImpuestoDao;
/*   8:    */ import com.asinfo.as2.dao.CiudadDao;
/*   9:    */ import com.asinfo.as2.dao.CondicionPagoDao;
/*  10:    */ import com.asinfo.as2.dao.ConfiguracionDao;
/*  11:    */ import com.asinfo.as2.dao.CuentaContableDao;
/*  12:    */ import com.asinfo.as2.dao.DocumentoContabilizacionDao;
/*  13:    */ import com.asinfo.as2.dao.DocumentoDao;
/*  14:    */ import com.asinfo.as2.dao.DocumentoVariableProcesoDao;
/*  15:    */ import com.asinfo.as2.dao.EstadoChequeDao;
/*  16:    */ import com.asinfo.as2.dao.EstadoCivilDao;
/*  17:    */ import com.asinfo.as2.dao.EstadoProcesoDao;
/*  18:    */ import com.asinfo.as2.dao.FiltroProductoDao;
/*  19:    */ import com.asinfo.as2.dao.FormaPagoDao;
/*  20:    */ import com.asinfo.as2.dao.GenericoDao;
/*  21:    */ import com.asinfo.as2.dao.IdiomaDao;
/*  22:    */ import com.asinfo.as2.dao.ImpuestoDao;
/*  23:    */ import com.asinfo.as2.dao.ListaPreciosDao;
/*  24:    */ import com.asinfo.as2.dao.ModuloDao;
/*  25:    */ import com.asinfo.as2.dao.MonedaDao;
/*  26:    */ import com.asinfo.as2.dao.MotivoAjusteInventarioDao;
/*  27:    */ import com.asinfo.as2.dao.OrganizacionConfiguracionDao;
/*  28:    */ import com.asinfo.as2.dao.OrganizacionDao;
/*  29:    */ import com.asinfo.as2.dao.PaisDao;
/*  30:    */ import com.asinfo.as2.dao.ParroquiaDao;
/*  31:    */ import com.asinfo.as2.dao.ProcesoOrganizacionDao;
/*  32:    */ import com.asinfo.as2.dao.ProvinciaDao;
/*  33:    */ import com.asinfo.as2.dao.PuntoDeVentaDao;
/*  34:    */ import com.asinfo.as2.dao.QuincenaDao;
/*  35:    */ import com.asinfo.as2.dao.RangoImpuestoDao;
/*  36:    */ import com.asinfo.as2.dao.ReporteadorDao;
/*  37:    */ import com.asinfo.as2.dao.RubroDao;
/*  38:    */ import com.asinfo.as2.dao.SecuenciaDao;
/*  39:    */ import com.asinfo.as2.dao.TemaDao;
/*  40:    */ import com.asinfo.as2.dao.TipoAsientoDao;
/*  41:    */ import com.asinfo.as2.dao.TipoBodegaDao;
/*  42:    */ import com.asinfo.as2.dao.TipoCuentaBancariaDao;
/*  43:    */ import com.asinfo.as2.dao.TipoIdentificacionDao;
/*  44:    */ import com.asinfo.as2.dao.UbicacionDao;
/*  45:    */ import com.asinfo.as2.dao.UnidadDao;
/*  46:    */ import com.asinfo.as2.dao.UsuarioBodegaDao;
/*  47:    */ import com.asinfo.as2.dao.UsuarioOrganizacionDao;
/*  48:    */ import com.asinfo.as2.dao.UsuarioSucursalDao;
/*  49:    */ import com.asinfo.as2.dao.ZonaDao;
/*  50:    */ import com.asinfo.as2.dao.nomina.asistencia.HoraExtraDao;
/*  51:    */ import com.asinfo.as2.dao.seguridad.AccionDao;
/*  52:    */ import com.asinfo.as2.dao.seguridad.PermisoDao;
/*  53:    */ import com.asinfo.as2.dao.seguridad.ProcesoDao;
/*  54:    */ import com.asinfo.as2.dao.seguridad.RolDao;
/*  55:    */ import com.asinfo.as2.dao.seguridad.SistemaDao;
/*  56:    */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*  57:    */ import com.asinfo.as2.dao.sri.ConceptoRetencionSRIDao;
/*  58:    */ import com.asinfo.as2.dao.sri.CreditoTributarioSRIDao;
/*  59:    */ import com.asinfo.as2.dao.sri.TipoComprobanteSRIDao;
/*  60:    */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*  61:    */ import com.asinfo.as2.entities.Banco;
/*  62:    */ import com.asinfo.as2.entities.CodigoFormaPagoSRI;
/*  63:    */ import com.asinfo.as2.entities.ComprobanteSRICreditoTributarioSRI;
/*  64:    */ import com.asinfo.as2.entities.ConfiguracionConciliacionBancaria;
/*  65:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*  66:    */ import com.asinfo.as2.entities.DetalleReporteador;
/*  67:    */ import com.asinfo.as2.entities.DetalleReporteadorVariable;
/*  68:    */ import com.asinfo.as2.entities.OrigenIngresos;
/*  69:    */ import com.asinfo.as2.entities.Plantilla;
/*  70:    */ import com.asinfo.as2.entities.Reportes;
/*  71:    */ import com.asinfo.as2.entities.ReportesPersonalizados;
/*  72:    */ import com.asinfo.as2.entities.TareaProgramada;
/*  73:    */ import com.asinfo.as2.entities.TipoIdentificacionComprobanteSRI;
/*  74:    */ import com.asinfo.as2.entities.VersionSistema;
/*  75:    */ import com.asinfo.as2.entities.sri.IBPCapacidad;
/*  76:    */ import com.asinfo.as2.entities.sri.IBPClasificacion;
/*  77:    */ import com.asinfo.as2.entities.sri.IBPMarca;
/*  78:    */ import com.asinfo.as2.entities.sri.IBPUnidad;
/*  79:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto;
/*  80:    */ import com.asinfo.as2.utils.ServiceLocator;
/*  81:    */ import java.io.File;
/*  82:    */ import javax.ejb.EJB;
/*  83:    */ 
/*  84:    */ public class ServicioCargarDatosInicialesDesdeXMLBase
/*  85:    */ {
/*  86:    */   @EJB
/*  87:    */   protected transient TipoIdentificacionDao tipoIdentificacionDao;
/*  88:    */   @EJB
/*  89:    */   protected transient OrganizacionDao organizacionDao;
/*  90:    */   @EJB
/*  91:    */   protected transient OrganizacionConfiguracionDao organizacionConfiguracionDao;
/*  92:    */   @EJB
/*  93:    */   protected transient UbicacionDao ubicacionDao;
/*  94:    */   @EJB
/*  95:    */   protected transient PuntoDeVentaDao puntoDeVentaDao;
/*  96:    */   @EJB
/*  97:    */   protected transient CajaDao cajaDao;
/*  98:    */   @EJB
/*  99:    */   protected transient UsuarioDao usuarioDao;
/* 100:    */   @EJB
/* 101:    */   protected transient ModuloDao moduloDao;
/* 102:    */   @EJB
/* 103:    */   protected transient IdiomaDao idiomaDao;
/* 104:    */   @EJB
/* 105:    */   protected transient UsuarioSucursalDao usuarioSucursalDao;
/* 106:    */   @EJB
/* 107:    */   protected transient ProcesoDao procesoDao;
/* 108:    */   @EJB
/* 109:    */   protected transient RolDao rolDao;
/* 110:    */   @EJB
/* 111:    */   protected transient PermisoDao permisoDao;
/* 112:    */   @EJB
/* 113:    */   protected transient AccionDao accionDao;
/* 114:    */   @EJB
/* 115:    */   protected transient ConfiguracionDao configuracionDao;
/* 116:    */   @EJB
/* 117:    */   protected transient FormaPagoDao formaPagoDao;
/* 118:    */   @EJB
/* 119:    */   protected transient DocumentoDao documentoDao;
/* 120:    */   @EJB
/* 121:    */   protected transient SecuenciaDao secuenciaDao;
/* 122:    */   @EJB
/* 123:    */   protected transient TipoComprobanteSRIDao tipoComprobanteSRIDao;
/* 124:    */   @EJB
/* 125:    */   protected transient CreditoTributarioSRIDao creditoTributarioSRIDao;
/* 126:    */   @EJB
/* 127:    */   protected transient TipoAsientoDao tipoAsientoDao;
/* 128:    */   @EJB
/* 129:    */   protected transient ConceptoRetencionSRIDao conceptoRetencionSRIDao;
/* 130:    */   @EJB
/* 131:    */   protected transient CondicionPagoDao condicionPagoDao;
/* 132:    */   @EJB
/* 133:    */   protected transient UnidadDao unidadDao;
/* 134:    */   @EJB
/* 135:    */   protected transient MonedaDao monedaDao;
/* 136:    */   @EJB
/* 137:    */   protected transient CategoriaImpuestoDao categoriaImpuestoDao;
/* 138:    */   @EJB
/* 139:    */   protected transient ImpuestoDao impuestoDao;
/* 140:    */   @EJB
/* 141:    */   protected transient PaisDao paisDao;
/* 142:    */   @EJB
/* 143:    */   protected transient TipoCuentaBancariaDao tipoCuentaBancariaDao;
/* 144:    */   @EJB
/* 145:    */   protected transient TemaDao temaDao;
/* 146:    */   @EJB
/* 147:    */   protected transient TipoBodegaDao tipoBodegaDao;
/* 148:    */   @EJB
/* 149:    */   protected transient BodegaDao bodegaDao;
/* 150:    */   @EJB
/* 151:    */   protected transient UsuarioBodegaDao usuarioBodegaDao;
/* 152:    */   @EJB
/* 153:    */   protected transient ProvinciaDao provinciaDao;
/* 154:    */   @EJB
/* 155:    */   protected transient CiudadDao ciudadDao;
/* 156:    */   @EJB
/* 157:    */   protected transient ServicioSucursal servicioSucursal;
/* 158:    */   @EJB
/* 159:    */   protected transient ServicioMoneda servicioMoneda;
/* 160:    */   @EJB
/* 161:    */   protected transient ListaPreciosDao listaPreciosDao;
/* 162:    */   @EJB
/* 163:    */   protected transient ZonaDao zonaDao;
/* 164:    */   @EJB
/* 165:    */   protected transient RangoImpuestoDao rangoImpuestoDao;
/* 166:    */   @EJB
/* 167:    */   protected transient ServicioCategoriaImpuesto servicioCategoriaImpuesto;
/* 168:    */   @EJB
/* 169:    */   protected transient CategoriaEmpresaDao categoriaEmpresaDao;
/* 170:    */   @EJB
/* 171:    */   protected transient DocumentoVariableProcesoDao documentoVariableProcesoDao;
/* 172:    */   @EJB
/* 173:    */   protected transient DocumentoContabilizacionDao documentoContabilizacionDao;
/* 174:    */   @EJB
/* 175:    */   protected transient SistemaDao sistemaDao;
/* 176:    */   @EJB
/* 177:    */   protected transient GenericoDao<Banco> bancoDao;
/* 178:    */   @EJB
/* 179:    */   protected transient QuincenaDao quincenaDao;
/* 180:    */   @EJB
/* 181:    */   protected transient RubroDao rubroDao;
/* 182:    */   @EJB
/* 183:    */   protected transient FiltroProductoDao filtroProductoDao;
/* 184:    */   @EJB
/* 185:    */   protected transient EstadoProcesoDao estadoProcesoDao;
/* 186:    */   @EJB
/* 187:    */   protected transient UsuarioOrganizacionDao usuarioOrganizacionDao;
/* 188:    */   @EJB
/* 189:    */   protected transient ProcesoOrganizacionDao procesoOrganizacionDao;
/* 190:    */   @EJB
/* 191:    */   protected transient ParroquiaDao parroquiaDao;
/* 192:    */   @EJB
/* 193:    */   protected transient HoraExtraDao horaExtraDao;
/* 194:    */   @EJB
/* 195:    */   protected transient GenericoDao<IBPClasificacion> ibpClasificacionDao;
/* 196:    */   @EJB
/* 197:    */   protected transient GenericoDao<IBPMarca> ibpMarcaDao;
/* 198:    */   @EJB
/* 199:    */   protected transient GenericoDao<IBPCapacidad> ibpCapacidadDao;
/* 200:    */   @EJB
/* 201:    */   protected transient GenericoDao<IBPUnidad> ibpUnidadDao;
/* 202:    */   @EJB
/* 203:    */   protected transient EstadoCivilDao estadoCivilDao;
/* 204:    */   @EJB
/* 205:    */   protected transient EstadoChequeDao estadoChequeDao;
/* 206:    */   @EJB
/* 207:    */   protected transient GenericoDao<TipoIdentificacionComprobanteSRI> tipoIdentificacionComprobanteSRIDao;
/* 208:    */   @EJB
/* 209:    */   protected transient GenericoDao<ComprobanteSRICreditoTributarioSRI> comprobanteSRICreditoTributarioSRIDao;
/* 210:    */   @EJB
/* 211:    */   protected transient GenericoDao<CuentaBancariaOrganizacion> cuentaBancariaOrganizacionDao;
/* 212:    */   @EJB
/* 213:    */   protected transient GenericoDao<ConfiguracionConciliacionBancaria> configuracionConciliacionBancariaDao;
/* 214:    */   @EJB
/* 215:    */   protected transient ReporteadorDao reporteadorDao;
/* 216:    */   @EJB
/* 217:    */   protected transient GenericoDao<DetalleReporteador> detalleReporteadorDAO;
/* 218:    */   @EJB
/* 219:    */   protected transient CuentaContableDao cuentaContableDao;
/* 220:    */   @EJB
/* 221:    */   protected transient GenericoDao<DetalleReporteadorVariable> detalleReporteadorVariableDao;
/* 222:    */   @EJB
/* 223:    */   protected transient GenericoDao<TareaProgramada> tareaProgramadaDao;
/* 224:    */   @EJB
/* 225:    */   protected transient GenericoDao<VersionSistema> versionSistemaDao;
/* 226:    */   @EJB
/* 227:    */   protected transient GenericoDao<Reportes> reportesDao;
/* 228:    */   @EJB
/* 229:    */   protected transient GenericoDao<ReportesPersonalizados> reportesPersonalizadosDao;
/* 230:    */   @EJB
/* 231:    */   protected transient GenericoDao<Plantilla> plantillaDao;
/* 232:    */   @EJB
/* 233:    */   protected transient MotivoAjusteInventarioDao motivoAjusteInventarioDao;
/* 234:    */   @EJB
/* 235:    */   protected transient GenericoDao<CodigoFormaPagoSRI> codigoFormaPagoSRIDao;
/* 236:    */   @EJB
/* 237:    */   protected transient GenericoDao<OrigenIngresos> origenIngreososDao;
/* 238:    */   private static String RUTA_ARCHIVO;
/* 239:263 */   protected static String RESOURCE_DATOS_INICIALES = "datos_iniciales";
/* 240:    */   
/* 241:    */   static
/* 242:    */   {
/* 243:267 */     File jbossDir = new File(System.getProperty("jboss.server.temp.dir"));
/* 244:268 */     File jbossTempDir = new File(jbossDir, File.separator + "vfs" + File.separator + "temp");
/* 245:    */     
/* 246:    */ 
/* 247:271 */     File tempDir = new File(jbossTempDir.getAbsolutePath() + File.separator + jbossTempDir.list()[0]);
/* 248:    */     
/* 249:273 */     String carpetaAS2 = "";
/* 250:274 */     for (String project : tempDir.list())
/* 251:    */     {
/* 252:275 */       String appName = tempDir.getAbsolutePath() + File.separator + project + File.separator + ServiceLocator.APP_NAME;
/* 253:276 */       if ((project.contains(ServiceLocator.APP_NAME + ".war")) || (new File(appName).isFile()))
/* 254:    */       {
/* 255:277 */         carpetaAS2 = project;
/* 256:278 */         break;
/* 257:    */       }
/* 258:    */     }
/* 259:281 */     File as2Dir = new File(tempDir.getAbsolutePath() + File.separator + carpetaAS2 + File.separator + "resources");
/* 260:282 */     RUTA_ARCHIVO = as2Dir.getAbsolutePath();
/* 261:    */   }
/* 262:    */   
/* 263:    */   public String getPathResoucesAS2(String nombreCarpeta)
/* 264:    */   {
/* 265:286 */     return RUTA_ARCHIVO + File.separator + nombreCarpeta + File.separator;
/* 266:    */   }
/* 267:    */   
/* 268:    */   protected boolean obtenerIndicador(String indicador)
/* 269:    */   {
/* 270:297 */     boolean flag = false;
/* 271:298 */     if ("1".equals(indicador)) {
/* 272:299 */       flag = true;
/* 273:    */     }
/* 274:301 */     if ("S".equals(indicador)) {
/* 275:302 */       flag = true;
/* 276:    */     }
/* 277:304 */     return flag;
/* 278:    */   }
/* 279:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioCargarDatosInicialesDesdeXMLBase
 * JD-Core Version:    0.7.0.1
 */