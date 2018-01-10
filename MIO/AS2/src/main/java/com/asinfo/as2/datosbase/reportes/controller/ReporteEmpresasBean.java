/*   1:    */ package com.asinfo.as2.datosbase.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import javax.faces.model.SelectItem;
/*  22:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  23:    */ import net.sf.jasperreports.engine.JRException;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ReporteEmpresasBean
/*  29:    */   extends AbstractBaseReportBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -916075102491017726L;
/*  32:    */   @EJB
/*  33:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  34:    */   @EJB
/*  35:    */   private ServicioEmpresa servicioEmpresa;
/*  36:    */   private int idCategoriaEmpresa;
/*  37:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  38:    */   private List<SelectItem> listaTipoEmpresa;
/*  39: 63 */   private boolean indicadorCliente = true;
/*  40:    */   private boolean indicadorProveedor;
/*  41:    */   private TipoEmpresa tipoEmpresa;
/*  42:    */   private Empresa empresa;
/*  43:    */   
/*  44:    */   private static enum enumClasificacion
/*  45:    */   {
/*  46: 69 */     Cliente,  Proveedor;
/*  47:    */     
/*  48:    */     private enumClasificacion() {}
/*  49:    */   }
/*  50:    */   
/*  51: 72 */   private enumClasificacion clasificacion = enumClasificacion.Cliente;
/*  52:    */   private List<SelectItem> listaClasificacion;
/*  53:    */   
/*  54:    */   protected JRDataSource getJRDataSource()
/*  55:    */   {
/*  56: 83 */     HashMap<Integer, Object[]> hmCuentas = new HashMap();
/*  57:    */     
/*  58: 85 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  59: 86 */     JRDataSource ds = null;
/*  60: 87 */     listaDatosReporte = this.servicioEmpresa.reporteEmpresas(AppUtil.getOrganizacion().getId(), this.indicadorCliente, this.indicadorProveedor, this.idCategoriaEmpresa, this.tipoEmpresa, this.empresa);
/*  61:    */     
/*  62:    */ 
/*  63: 90 */     List<Object[]> listaCuentaBancaria = this.servicioEmpresa.listaCuentaBancariaEmpresa(AppUtil.getOrganizacion().getId());
/*  64: 92 */     for (Object[] objects : listaCuentaBancaria) {
/*  65: 93 */       hmCuentas.put((Integer)objects[0], objects);
/*  66:    */     }
/*  67: 96 */     if (!this.indicadorCliente) {
/*  68: 97 */       for (Object[] objects : listaDatosReporte)
/*  69:    */       {
/*  70: 98 */         Object[] cuentas = (Object[])hmCuentas.get((Integer)objects[0]);
/*  71:100 */         if (cuentas != null)
/*  72:    */         {
/*  73:101 */           objects[25] = cuentas[2];
/*  74:102 */           objects[26] = cuentas[3];
/*  75:103 */           objects[27] = cuentas[4];
/*  76:104 */           objects[28] = cuentas[5];
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:    */     String[] fields;
/*  81:    */     String[] fields;
/*  82:110 */     if (this.indicadorCliente) {
/*  83:111 */       fields = new String[] { "f_idEmpresa", "f_codigo", "f_tipoIdentificacion", "f_identificacion", "f_nombreComercial", "f_nombreFiscal", "f_categoria", "f_tipoEmpresa", "f_nota", "f_pais", "f_provincia", "f_ciudad", "f_direccion", "f_tipoCliente", "f_zona", "f_agenteComercial", "f_recaudador", "f_formaCobro", "f_condicionCobro", "f_numeroCuotas", "f_metodoFacturacion", "f_listaPrecios", "f_listaDescuentos", "f_creditoMaximo", "f_creditoUtilizado", "f_excentoImpuestos", "f_contacto", "f_transportista", "f_email1", "f_telefono1", "f_telefono2" };
/*  84:    */     } else {
/*  85:117 */       fields = new String[] { "f_idEmpresa", "f_codigo", "f_tipoIdentificacion", "f_identificacion", "f_nombreComercial", "f_nombreFiscal", "f_categoria", "f_tipoEmpresa", "f_nota", "f_pais", "f_provincia", "f_ciudad", "f_direccion", "f_formaPago", "f_condicionPago", "f_categoriaRetencion", "f_numeroCuotas", "f_listaPrecios", "f_pagoCash", "f_beneficiario", "f_contacto", "f_parteRelacionada", "f_email1", "f_telefono1", "f_telefono2", "f_codigoBanco", "f_nombreBanco", "f_numeroCuentaBancaria", "f_tipoCuentaBancaria" };
/*  86:    */     }
/*  87:122 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  88:123 */     return ds;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Map<String, Object> getReportParameters()
/*  92:    */   {
/*  93:133 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  94:134 */     if (this.indicadorCliente) {
/*  95:135 */       reportParameters.put("ReportTitle", "Reporte Clientes");
/*  96:    */     } else {
/*  97:137 */       reportParameters.put("ReportTitle", "Reporte Proveedores");
/*  98:    */     }
/*  99:139 */     return reportParameters;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String execute()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:149 */       super.prepareReport();
/* 107:    */     }
/* 108:    */     catch (JRException e)
/* 109:    */     {
/* 110:151 */       LOG.info("Error JRException");
/* 111:152 */       e.printStackTrace();
/* 112:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos") + " " + e.getMessage());
/* 113:    */     }
/* 114:    */     catch (IOException e)
/* 115:    */     {
/* 116:155 */       LOG.info("Error IOException");
/* 117:156 */       e.printStackTrace();
/* 118:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos") + " " + e.getMessage());
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:159 */       LOG.error("Error al generar el reporte de Cliente/Proveedor");
/* 123:160 */       e.printStackTrace();
/* 124:161 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 125:    */     }
/* 126:164 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public List<Empresa> autocompletarEmpresa(String consulta)
/* 130:    */   {
/* 131:168 */     if (this.clasificacion == enumClasificacion.Cliente) {
/* 132:169 */       return this.servicioEmpresa.autocompletarClientes(consulta);
/* 133:    */     }
/* 134:171 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected String getCompileFileName()
/* 138:    */   {
/* 139:183 */     if (this.indicadorCliente) {
/* 140:184 */       return "reporteEmpresasCliente";
/* 141:    */     }
/* 142:186 */     return "reporteEmpresasProveedor";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int getIdCategoriaEmpresa()
/* 146:    */   {
/* 147:196 */     return this.idCategoriaEmpresa;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setIdCategoriaEmpresa(int idCategoriaEmpresa)
/* 151:    */   {
/* 152:206 */     this.idCategoriaEmpresa = idCategoriaEmpresa;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 156:    */   {
/* 157:215 */     if (this.listaCategoriaEmpresa == null) {
/* 158:216 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, null);
/* 159:    */     }
/* 160:218 */     return this.listaCategoriaEmpresa;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 164:    */   {
/* 165:228 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public List<SelectItem> getListaTipoEmpresa()
/* 169:    */   {
/* 170:238 */     if (this.listaTipoEmpresa == null)
/* 171:    */     {
/* 172:239 */       this.listaTipoEmpresa = new ArrayList();
/* 173:240 */       for (TipoEmpresa t : TipoEmpresa.values())
/* 174:    */       {
/* 175:241 */         SelectItem item = new SelectItem(t, t.getNombre());
/* 176:242 */         this.listaTipoEmpresa.add(item);
/* 177:    */       }
/* 178:    */     }
/* 179:246 */     return this.listaTipoEmpresa;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public enumClasificacion getClasificacion()
/* 183:    */   {
/* 184:255 */     return this.clasificacion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setClasificacion(enumClasificacion clasificacion)
/* 188:    */   {
/* 189:265 */     this.clasificacion = clasificacion;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<SelectItem> getListaClasificacion()
/* 193:    */   {
/* 194:272 */     if (this.listaClasificacion == null)
/* 195:    */     {
/* 196:273 */       this.listaClasificacion = new ArrayList();
/* 197:274 */       for (enumClasificacion clasificacion : enumClasificacion.values())
/* 198:    */       {
/* 199:275 */         SelectItem item = new SelectItem(clasificacion, clasificacion.name());
/* 200:276 */         this.listaClasificacion.add(item);
/* 201:    */       }
/* 202:    */     }
/* 203:279 */     return this.listaClasificacion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setListaClasificacion(List<SelectItem> listaClasificacion)
/* 207:    */   {
/* 208:287 */     this.listaClasificacion = listaClasificacion;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean isIndicadorCliente()
/* 212:    */   {
/* 213:291 */     return this.indicadorCliente;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 217:    */   {
/* 218:295 */     this.indicadorCliente = indicadorCliente;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean isIndicadorProveedor()
/* 222:    */   {
/* 223:299 */     return this.indicadorProveedor;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setIndicadorProveedor(boolean indicadorProveedor)
/* 227:    */   {
/* 228:303 */     this.indicadorProveedor = indicadorProveedor;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void actualizarClasificacion()
/* 232:    */   {
/* 233:307 */     if (this.clasificacion.equals(enumClasificacion.Cliente))
/* 234:    */     {
/* 235:308 */       this.indicadorCliente = true;
/* 236:309 */       this.indicadorProveedor = false;
/* 237:    */     }
/* 238:    */     else
/* 239:    */     {
/* 240:312 */       this.indicadorProveedor = true;
/* 241:313 */       this.indicadorCliente = false;
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public TipoEmpresa getTipoEmpresa()
/* 246:    */   {
/* 247:318 */     return this.tipoEmpresa;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setTipoEmpresa(TipoEmpresa tipoEmpresa)
/* 251:    */   {
/* 252:322 */     this.tipoEmpresa = tipoEmpresa;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public Empresa getEmpresa()
/* 256:    */   {
/* 257:326 */     return this.empresa;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setEmpresa(Empresa empresa)
/* 261:    */   {
/* 262:330 */     this.empresa = empresa;
/* 263:    */   }
/* 264:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.reportes.controller.ReporteEmpresasBean
 * JD-Core Version:    0.7.0.1
 */