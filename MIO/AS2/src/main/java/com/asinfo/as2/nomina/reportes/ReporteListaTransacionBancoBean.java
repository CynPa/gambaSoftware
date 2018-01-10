/*   1:    */ package com.asinfo.as2.nomina.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PagoRol;
/*   7:    */ import com.asinfo.as2.entities.Quincena;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
/*  10:    */ import com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReporteListaTransacionBancoBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReporteNomina servicioReporteNomina;
/*  37:    */   @EJB
/*  38:    */   private ServicioPagoRol servicioPagoRol;
/*  39:    */   @EJB
/*  40:    */   private ServicioSucursal servicioSucursal;
/*  41:    */   private List<SelectItem> listaPagoRol;
/*  42:    */   private List<Sucursal> listaSucursal;
/*  43:    */   private PagoRol pagoRol;
/*  44:    */   private List<SelectItem> listaFormaPagoEmpleado;
/*  45:    */   private FormaPagoEmpleado formaPagoEmpleado;
/*  46:    */   private Sucursal sucursal;
/*  47: 66 */   private boolean indicadorPagoCash = true;
/*  48:    */   
/*  49:    */   protected JRDataSource getJRDataSource()
/*  50:    */   {
/*  51: 77 */     List listaDatosReporte = new ArrayList();
/*  52: 78 */     JRDataSource ds = null;
/*  53:    */     boolean aprobados;
/*  54:    */     boolean aprobados;
/*  55: 80 */     if (ParametrosSistema.getIndicadorAprobarNomina(AppUtil.getOrganizacion().getId()).booleanValue()) {
/*  56: 81 */       aprobados = true;
/*  57:    */     } else {
/*  58: 83 */       aprobados = false;
/*  59:    */     }
/*  60: 86 */     listaDatosReporte = this.servicioReporteNomina.getListaTransacionBancos(this.pagoRol, this.formaPagoEmpleado, this.sucursal, AppUtil.getOrganizacion().getId(), aprobados, this.indicadorPagoCash);
/*  61:    */     
/*  62:    */ 
/*  63: 89 */     String[] fields = { "codigo", "nombreEmpleado", "identificacion", "ingresos", "egresos", "provisiones", "numeroCuenta", "fechaPagoRol" };
/*  64:    */     
/*  65: 91 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  66: 92 */     return ds;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected Map<String, Object> getReportParameters()
/*  70:    */   {
/*  71:103 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  72:104 */     reportParameters.put("ReportTitle", "Listado De Transacciones");
/*  73:105 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/*  74:106 */     reportParameters.put("FechaRol", FuncionesUtiles.nombreMes(this.pagoRol.getMes() - 1) + "-" + Integer.toString(this.pagoRol.getAnio()));
/*  75:107 */     return reportParameters;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String execute()
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:117 */       super.prepareReport();
/*  83:    */     }
/*  84:    */     catch (JRException e)
/*  85:    */     {
/*  86:119 */       LOG.info("Error JRException");
/*  87:120 */       e.printStackTrace();
/*  88:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  89:    */     }
/*  90:    */     catch (IOException e)
/*  91:    */     {
/*  92:123 */       LOG.info("Error IOException");
/*  93:124 */       e.printStackTrace();
/*  94:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  95:    */     }
/*  96:128 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected String getCompileFileName()
/* 100:    */   {
/* 101:139 */     return "reporteListaTransaccionBanco";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<SelectItem> getListaPagoRol()
/* 105:    */   {
/* 106:150 */     if (this.listaPagoRol == null)
/* 107:    */     {
/* 108:151 */       List<PagoRol> lista = new ArrayList();
/* 109:152 */       Map<String, String> filters = new HashMap();
/* 110:153 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 111:154 */       filters.put("indicadorFiniquito", "false");
/* 112:    */       
/* 113:156 */       lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 114:157 */       if (this.listaPagoRol == null)
/* 115:    */       {
/* 116:158 */         this.listaPagoRol = new ArrayList();
/* 117:159 */         for (PagoRol pagoRol : lista)
/* 118:    */         {
/* 119:163 */           String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 120:164 */           SelectItem item = new SelectItem(pagoRol, label);
/* 121:165 */           this.listaPagoRol.add(item);
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:170 */     return this.listaPagoRol;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public PagoRol getPagoRol()
/* 129:    */   {
/* 130:179 */     if (this.pagoRol == null) {
/* 131:180 */       this.pagoRol = new PagoRol();
/* 132:    */     }
/* 133:182 */     return this.pagoRol;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setPagoRol(PagoRol pagoRol)
/* 137:    */   {
/* 138:192 */     this.pagoRol = pagoRol;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<SelectItem> getListaFormaPagoEmpleado()
/* 142:    */   {
/* 143:201 */     if (this.listaFormaPagoEmpleado == null)
/* 144:    */     {
/* 145:202 */       this.listaFormaPagoEmpleado = new ArrayList();
/* 146:203 */       for (FormaPagoEmpleado formaPagoEmpleado : FormaPagoEmpleado.values())
/* 147:    */       {
/* 148:204 */         SelectItem item = new SelectItem(formaPagoEmpleado, formaPagoEmpleado.getNombre());
/* 149:205 */         this.listaFormaPagoEmpleado.add(item);
/* 150:    */       }
/* 151:    */     }
/* 152:208 */     return this.listaFormaPagoEmpleado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public FormaPagoEmpleado getFormaPagoEmpleado()
/* 156:    */   {
/* 157:217 */     return this.formaPagoEmpleado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/* 161:    */   {
/* 162:227 */     this.formaPagoEmpleado = formaPagoEmpleado;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Sucursal getSucursal()
/* 166:    */   {
/* 167:237 */     if (this.sucursal == null) {
/* 168:238 */       this.sucursal = new Sucursal();
/* 169:    */     }
/* 170:240 */     return this.sucursal;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setSucursal(Sucursal sucursal)
/* 174:    */   {
/* 175:250 */     this.sucursal = sucursal;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<Sucursal> getListaSucursal()
/* 179:    */   {
/* 180:259 */     if (this.listaSucursal == null) {
/* 181:260 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 182:    */     }
/* 183:262 */     return this.listaSucursal;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isIndicadorPagoCash()
/* 187:    */   {
/* 188:267 */     return this.indicadorPagoCash;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setIndicadorPagoCash(boolean indicadorPagoCash)
/* 192:    */   {
/* 193:271 */     this.indicadorPagoCash = indicadorPagoCash;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteListaTransacionBancoBean
 * JD-Core Version:    0.7.0.1
 */