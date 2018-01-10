/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Ruta;
/*   8:    */ import com.asinfo.as2.entities.Transportista;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  10:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Calendar;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.annotation.PostConstruct;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class ReporteLogisticaTransportistaBean
/*  33:    */   extends AbstractBaseReportBean
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 3615647209557356681L;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioTransportista servicioTransportista;
/*  38:    */   @EJB
/*  39:    */   private ServicioEmpresa servicioEmpresa;
/*  40:    */   @EJB
/*  41:    */   private ServicioGenerico<Ruta> servicioRuta;
/*  42:    */   @EJB
/*  43:    */   private ServicioPedidoCliente servicioPedidoCliente;
/*  44:    */   private Date fechaDesde;
/*  45:    */   private Date fechaHasta;
/*  46:    */   private List<Transportista> listaTransportista;
/*  47:    */   private Transportista transportista;
/*  48:    */   private Ruta ruta;
/*  49:    */   private Empresa empresa;
/*  50:    */   
/*  51:    */   protected JRDataSource getJRDataSource()
/*  52:    */   {
/*  53: 80 */     List listaDatosReporte = new ArrayList();
/*  54: 81 */     JRDataSource ds = null;
/*  55:    */     
/*  56: 83 */     listaDatosReporte = this.servicioPedidoCliente.getReporteLogisticaTransportista(this.fechaDesde, this.fechaHasta, AppUtil.getOrganizacion().getId(), this.transportista, this.ruta, this.empresa);
/*  57:    */     
/*  58:    */ 
/*  59: 86 */     String[] fields = { "f_numeroPedido", "f_fechaPedido", "f_fechaDespacho", "f_identificacionEmpresa", "f_nombreEmpresa", "f_transportista", "f_chofer", "f_placa", "f_ruta", "f_totalPedido" };
/*  60:    */     
/*  61:    */ 
/*  62: 89 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  63: 90 */     return ds;
/*  64:    */   }
/*  65:    */   
/*  66:    */   @PostConstruct
/*  67:    */   public void init()
/*  68:    */   {
/*  69: 95 */     Calendar calfechaDesde = Calendar.getInstance();
/*  70: 96 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  71: 97 */     this.fechaDesde = calfechaDesde.getTime();
/*  72: 98 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<Empresa> autocompletarClientes(String consulta)
/*  76:    */   {
/*  77:102 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected String getCompileFileName()
/*  81:    */   {
/*  82:112 */     return "reportelogisticaTransportista";
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected Map<String, Object> getReportParameters()
/*  86:    */   {
/*  87:123 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  88:124 */     reportParameters.put("ReportTitle", "Reporte Logistica Transportistas");
/*  89:125 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/*  90:126 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/*  91:127 */     reportParameters.put("p_ruta", this.ruta == null ? "TODOS" : this.ruta.getRuta());
/*  92:128 */     reportParameters.put("p_transportista", this.transportista == null ? "TODOS" : this.transportista.getNombre());
/*  93:129 */     reportParameters.put("p_cliente", this.empresa == null ? "TODOS" : this.empresa.getNombreFiscal());
/*  94:    */     
/*  95:131 */     return reportParameters;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String execute()
/*  99:    */   {
/* 100:    */     try
/* 101:    */     {
/* 102:142 */       if (this.fechaDesde == null) {
/* 103:143 */         this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 104:    */       }
/* 105:145 */       if (this.fechaHasta == null) {
/* 106:146 */         this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 107:    */       }
/* 108:149 */       super.prepareReport();
/* 109:    */     }
/* 110:    */     catch (JRException e)
/* 111:    */     {
/* 112:151 */       LOG.info("Error JRException");
/* 113:152 */       e.printStackTrace();
/* 114:153 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 115:    */     }
/* 116:    */     catch (IOException e)
/* 117:    */     {
/* 118:155 */       LOG.info("Error IOException");
/* 119:156 */       e.printStackTrace();
/* 120:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 121:    */     }
/* 122:160 */     return null;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<Ruta> autocompletarRuta(String consulta)
/* 126:    */   {
/* 127:164 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 128:165 */     filters.put("OR~ciudadOrigen.nombre", "%" + consulta + "%");
/* 129:166 */     filters.put("OR~ciudadDestino.nombre", "%" + consulta + "%");
/* 130:167 */     filters.put("OR~tipoVehiculo.nombre", "%" + consulta + "%");
/* 131:168 */     filters.put("OR~ruta", "%" + consulta + "%");
/* 132:    */     
/* 133:170 */     List<String> listaCampos = new ArrayList();
/* 134:171 */     listaCampos.add("ciudadOrigen");
/* 135:172 */     listaCampos.add("ciudadDestino");
/* 136:173 */     listaCampos.add("tipoVehiculo");
/* 137:174 */     List<Ruta> listaRuta = this.servicioRuta.obtenerListaPorPagina(Ruta.class, 0, 50, "ciudadOrigen", true, filters, listaCampos);
/* 138:175 */     return listaRuta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Date getFechaDesde()
/* 142:    */   {
/* 143:179 */     return this.fechaDesde;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setFechaDesde(Date fechaDesde)
/* 147:    */   {
/* 148:183 */     this.fechaDesde = fechaDesde;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Date getFechaHasta()
/* 152:    */   {
/* 153:187 */     return this.fechaHasta;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setFechaHasta(Date fechaHasta)
/* 157:    */   {
/* 158:191 */     this.fechaHasta = fechaHasta;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<Transportista> getListaTransportista()
/* 162:    */   {
/* 163:195 */     if (this.listaTransportista == null)
/* 164:    */     {
/* 165:196 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 166:197 */       filters.put("activo", "=true");
/* 167:198 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 168:    */     }
/* 169:200 */     return this.listaTransportista;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Transportista getTransportista()
/* 173:    */   {
/* 174:204 */     return this.transportista;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setTransportista(Transportista transportista)
/* 178:    */   {
/* 179:208 */     this.transportista = transportista;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Empresa getEmpresa()
/* 183:    */   {
/* 184:212 */     return this.empresa;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setEmpresa(Empresa empresa)
/* 188:    */   {
/* 189:216 */     this.empresa = empresa;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Ruta getRuta()
/* 193:    */   {
/* 194:220 */     return this.ruta;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setRuta(Ruta ruta)
/* 198:    */   {
/* 199:224 */     this.ruta = ruta;
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteLogisticaTransportistaBean
 * JD-Core Version:    0.7.0.1
 */