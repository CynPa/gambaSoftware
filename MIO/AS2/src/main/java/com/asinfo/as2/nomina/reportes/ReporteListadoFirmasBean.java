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
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.RequestScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import net.sf.jasperreports.engine.JRException;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @RequestScoped
/*  30:    */ public class ReporteListadoFirmasBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 5368588440437967863L;
/*  34:    */   @EJB
/*  35:    */   private ServicioReporteNomina servicioReporteNomina;
/*  36:    */   @EJB
/*  37:    */   private ServicioPagoRol servicioPagoRol;
/*  38:    */   @EJB
/*  39:    */   private ServicioSucursal servicioSucursal;
/*  40:    */   private List<SelectItem> listaPagoRol;
/*  41:    */   private List<SelectItem> listaFormaPagoEmpleado;
/*  42:    */   private List<Sucursal> listaSucursal;
/*  43:    */   private PagoRol pagoRol;
/*  44:    */   private FormaPagoEmpleado formaPagoEmpleado;
/*  45:    */   private Sucursal sucursal;
/*  46:    */   
/*  47:    */   protected JRDataSource getJRDataSource()
/*  48:    */   {
/*  49: 76 */     List listaDatosReporte = new ArrayList();
/*  50: 77 */     JRDataSource ds = null;
/*  51: 78 */     listaDatosReporte = this.servicioReporteNomina.getListaFirmas(this.pagoRol, this.formaPagoEmpleado, this.sucursal, AppUtil.getOrganizacion().getId());
/*  52:    */     
/*  53: 80 */     String[] fields = { "codigo", "nombreEmpleado", "identificacion", "ingresos", "egresos", "provisiones", "fechaPagoRol" };
/*  54: 81 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  55: 82 */     return ds;
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected Map<String, Object> getReportParameters()
/*  59:    */   {
/*  60: 93 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  61: 94 */     reportParameters.put("ReportTitle", "Listado De Firmas");
/*  62: 95 */     this.pagoRol = this.servicioPagoRol.buscarPorId(this.pagoRol.getIdPagoRol());
/*  63: 96 */     reportParameters.put("FechaRol", FuncionesUtiles.nombreMes(this.pagoRol.getMes() - 1) + "-" + Integer.toString(this.pagoRol.getAnio()));
/*  64:    */     
/*  65: 98 */     return reportParameters;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String execute()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:109 */       super.prepareReport();
/*  73:    */     }
/*  74:    */     catch (JRException e)
/*  75:    */     {
/*  76:111 */       LOG.info("Error JRException");
/*  77:112 */       e.printStackTrace();
/*  78:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  79:    */     }
/*  80:    */     catch (IOException e)
/*  81:    */     {
/*  82:115 */       LOG.info("Error IOException");
/*  83:116 */       e.printStackTrace();
/*  84:117 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  85:    */     }
/*  86:120 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected String getCompileFileName()
/*  90:    */   {
/*  91:131 */     return "reporteListaFirmas";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<SelectItem> getListaPagoRol()
/*  95:    */   {
/*  96:142 */     if (this.listaPagoRol == null)
/*  97:    */     {
/*  98:143 */       List<PagoRol> lista = new ArrayList();
/*  99:144 */       Map<String, String> filters = new HashMap();
/* 100:145 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 101:146 */       filters.put("indicadorFiniquito", "false");
/* 102:    */       
/* 103:148 */       lista = this.servicioPagoRol.obtenerListaCombo("fecha", false, filters);
/* 104:149 */       if (this.listaPagoRol == null)
/* 105:    */       {
/* 106:150 */         this.listaPagoRol = new ArrayList();
/* 107:151 */         for (PagoRol pagoRol : lista)
/* 108:    */         {
/* 109:157 */           String label = pagoRol.getQuincena().getNombre() + "\t|\t" + FuncionesUtiles.dateToString(pagoRol.getFecha()) + "\t|\t" + (!pagoRol.isIndicadorFiniquito() ? FuncionesUtiles.nombreMes(pagoRol.getMes() - 1) + "-" + Integer.toString(pagoRol.getAnio()) : new StringBuilder().append(" Finiquito: ").append(pagoRol.getNombreEmpleadoFiniquito()).toString());
/* 110:158 */           SelectItem item = new SelectItem(pagoRol, label);
/* 111:159 */           this.listaPagoRol.add(item);
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:164 */     return this.listaPagoRol;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public PagoRol getPagoRol()
/* 119:    */   {
/* 120:173 */     if (this.pagoRol == null) {
/* 121:174 */       this.pagoRol = new PagoRol();
/* 122:    */     }
/* 123:176 */     return this.pagoRol;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPagoRol(PagoRol pagoRol)
/* 127:    */   {
/* 128:186 */     this.pagoRol = pagoRol;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<SelectItem> getListaFormaPagoEmpleado()
/* 132:    */   {
/* 133:195 */     if (this.listaFormaPagoEmpleado == null)
/* 134:    */     {
/* 135:196 */       this.listaFormaPagoEmpleado = new ArrayList();
/* 136:197 */       for (FormaPagoEmpleado formaPagoEmpleado : FormaPagoEmpleado.values())
/* 137:    */       {
/* 138:198 */         SelectItem item = new SelectItem(formaPagoEmpleado, formaPagoEmpleado.getNombre());
/* 139:199 */         this.listaFormaPagoEmpleado.add(item);
/* 140:    */       }
/* 141:    */     }
/* 142:202 */     return this.listaFormaPagoEmpleado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public FormaPagoEmpleado getFormaPagoEmpleado()
/* 146:    */   {
/* 147:211 */     return this.formaPagoEmpleado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFormaPagoEmpleado(FormaPagoEmpleado formaPagoEmpleado)
/* 151:    */   {
/* 152:221 */     this.formaPagoEmpleado = formaPagoEmpleado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<Sucursal> getListaSucursal()
/* 156:    */   {
/* 157:230 */     if (this.listaSucursal == null) {
/* 158:231 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 159:    */     }
/* 160:233 */     return this.listaSucursal;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 164:    */   {
/* 165:243 */     this.listaSucursal = listaSucursal;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Sucursal getSucursal()
/* 169:    */   {
/* 170:252 */     if (this.sucursal == null) {
/* 171:253 */       this.sucursal = new Sucursal();
/* 172:    */     }
/* 173:255 */     return this.sucursal;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setSucursal(Sucursal sucursal)
/* 177:    */   {
/* 178:265 */     this.sucursal = sucursal;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ReporteListadoFirmasBean
 * JD-Core Version:    0.7.0.1
 */