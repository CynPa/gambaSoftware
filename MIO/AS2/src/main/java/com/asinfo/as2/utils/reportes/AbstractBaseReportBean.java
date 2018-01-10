/*   1:    */ package com.asinfo.as2.utils.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageController;
/*   7:    */ import com.asinfo.as2.entities.Ciudad;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Ubicacion;
/*  12:    */ import com.asinfo.as2.enumeraciones.ExportOption;
/*  13:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.util.RutaArchivo;
/*  16:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  17:    */ import java.io.File;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.io.PrintWriter;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.context.ExternalContext;
/*  26:    */ import javax.faces.context.FacesContext;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import javax.servlet.http.HttpServletRequest;
/*  29:    */ import javax.servlet.http.HttpServletResponse;
/*  30:    */ import javax.servlet.http.HttpSession;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import net.sf.jasperreports.engine.JRPrintPage;
/*  34:    */ import net.sf.jasperreports.engine.JasperPrint;
/*  35:    */ 
/*  36:    */ public abstract class AbstractBaseReportBean
/*  37:    */   extends PageController
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 1210397383511632691L;
/*  40: 55 */   private boolean documentoElectronico = false;
/*  41:    */   private ExportOption exportOption;
/*  42:    */   private List<SelectItem> listaExportOption;
/*  43: 61 */   private final String COMPILE_DIR = "/reportes/";
/*  44:    */   @EJB
/*  45:    */   private transient ServicioSucursal servicioSucursal;
/*  46:    */   
/*  47:    */   public AbstractBaseReportBean()
/*  48:    */   {
/*  49: 68 */     setExportOption(ExportOption.PDF);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected String getPathReportes()
/*  53:    */   {
/*  54: 72 */     return ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getId()) + getCompileDir();
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected void prepareReport()
/*  58:    */     throws JRException, IOException
/*  59:    */   {
/*  60: 76 */     prepareReport(null);
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected JasperPrint prepareReport(JRDataSource dataSource)
/*  64:    */     throws JRException, IOException
/*  65:    */   {
/*  66: 81 */     String pathReportes = null;
/*  67: 82 */     if (this.documentoElectronico) {
/*  68: 83 */       pathReportes = ServicioConfiguracion.DIRECTORIO_SRI;
/*  69:    */     } else {
/*  70: 85 */       pathReportes = ParametrosSistema.getAS2_HOME(AppUtil.getOrganizacion().getId());
/*  71:    */     }
/*  72: 88 */     ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
/*  73: 89 */     HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
/*  74: 90 */     HttpServletResponse response = (HttpServletResponse)externalContext.getResponse();
/*  75:    */     
/*  76: 92 */     File reportFile = new File(ReportConfigUtil.getJasperFilePath(pathReportes, getCompileDir(), getCompileFileName() + ".jasper"));
/*  77: 93 */     ReportConfigUtil.compileReport(pathReportes, getCompileDir(), getCompileFileName(), getReportParameters().containsKey("SUBREPORT_DIR"));
/*  78: 95 */     if (dataSource != null) {
/*  79: 96 */       return ReportConfigUtil.fillReport(reportFile, getReportParameters(), dataSource);
/*  80:    */     }
/*  81:103 */     JRDataSource jrDataSource = getJRDataSource();
/*  82:104 */     JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, getReportParameters(), jrDataSource);
/*  83:106 */     if ((jasperPrint.getPages().isEmpty()) || (((JRPrintPage)jasperPrint.getPages().get(0)).getElements().isEmpty()))
/*  84:    */     {
/*  85:107 */       addErrorMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:110 */       jasperPrint.setProperty("net.sf.jasperreports.print.keep.full.text", "true");
/*  90:111 */       jasperPrint.setProperty("net.sf.jasperreports.export.xls.wrap.text", "false");
/*  91:112 */       jasperPrint.setProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
/*  92:114 */       if (getExportOption().equals(ExportOption.HTML))
/*  93:    */       {
/*  94:115 */         ReportConfigUtil.exportReportAsHtml(jasperPrint, response.getWriter());
/*  95:116 */         FacesContext.getCurrentInstance().responseComplete();
/*  96:    */       }
/*  97:117 */       else if (getExportOption().equals(ExportOption.PRINTER))
/*  98:    */       {
/*  99:119 */         request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
/* 100:    */         
/* 101:121 */         String url = request.getRequestURL().toString();
/* 102:122 */         int b = url.substring(url.indexOf("/paginas")).split("/").length;
/* 103:    */         
/* 104:124 */         String codebase = "";
/* 105:125 */         for (int i = 1; i <= b - 2; i++) {
/* 106:126 */           codebase = codebase + "../";
/* 107:    */         }
/* 108:128 */         codebase = codebase + "applets";
/* 109:    */         
/* 110:130 */         response.setContentType("text/html");
/* 111:131 */         PrintWriter out = response.getWriter();
/* 112:132 */         out.println("<APPLET  CODE = 'PrinterApplet.class' CODEBASE = '" + codebase + "' ARCHIVE = 'print-applet-1.0.jar,commons-digester.jar,javax.servlet-3.0.0.v201112011016.jar,jasperreports-5.5.0.jar,jasperreports-applet-5.5.0.jar,commons-logging-1.1.1.jar,commons-collections-2.1.1.jar' WIDTH = '0' HEIGHT = '0'>");
/* 113:    */         
/* 114:    */ 
/* 115:135 */         out.println("<PARAM NAME = CODE VALUE = 'PrinterApplet.class' >");
/* 116:136 */         out.println("<PARAM NAME = CODEBASE VALUE = '" + codebase + "' >");
/* 117:137 */         out.println("<PARAM NAME = ARCHIVE VALUE = 'print-applet-1.0.jar,commons-digester.jar,javax.servlet-3.0.0.v201112011016.jar,jasperreports-5.5.0.jar,jasperreports-applet-5.5.0.jar,commons-logging-1.1.1.jar,commons-collections-2.1.1.jar' >");
/* 118:138 */         out.println("<PARAM NAME='type' VALUE='application/x-java-applet;version=1.2.2'>");
/* 119:139 */         out.println("<PARAM NAME='scriptable' VALUE='false'>");
/* 120:140 */         out.println("<PARAM NAME = 'REPORT_URL' VALUE ='../servlets/print'>");
/* 121:141 */         out.println("</APPLET>");
/* 122:    */       }
/* 123:    */       else
/* 124:    */       {
/* 125:144 */         setearJasperSession(request);
/* 126:145 */         request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
/* 127:146 */         response.sendRedirect(request.getContextPath() + "/servlets/report/" + getExportOption());
/* 128:147 */         FacesContext.getCurrentInstance().responseComplete();
/* 129:    */       }
/* 130:    */     }
/* 131:151 */     return null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected void setearJasperSession(HttpServletRequest request)
/* 135:    */   {
/* 136:155 */     request.getSession().setAttribute("jrprintlist", null);
/* 137:156 */     request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print_list", null);
/* 138:157 */     request.getSession().setAttribute("jrprint", null);
/* 139:158 */     request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", null);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public ExportOption getExportOption()
/* 143:    */   {
/* 144:162 */     return this.exportOption;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setExportOption(ExportOption exportOption)
/* 148:    */   {
/* 149:166 */     this.exportOption = exportOption;
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected Map<String, Object> getReportParameters()
/* 153:    */   {
/* 154:170 */     Map<String, Object> reportParameters = new HashMap();
/* 155:    */     
/* 156:172 */     Sucursal sucursal = this.servicioSucursal.cargarDetalle(AppUtil.getSucursal().getIdSucursal());
/* 157:173 */     reportParameters.put("nombreOrganizacion", AppUtil.getOrganizacion().getRazonSocial());
/* 158:174 */     reportParameters.put("p_direccionMatriz", AppUtil.getDireccionMatriz() == null ? "" : AppUtil.getDireccionMatriz());
/* 159:175 */     reportParameters.put("direccionOrganizacion", sucursal.getUbicacion().getDireccionCompleta());
/* 160:176 */     reportParameters.put("p_numeroResolucionContribuyente", AppUtil.getOrganizacion().getOrganizacionConfiguracion()
/* 161:177 */       .getNumeroResolucionContribuyente());
/* 162:178 */     reportParameters.put("telefonoOrganizacion", sucursal.getTelefono1().concat(sucursal.getTelefono2() != null ? "   " + sucursal.getTelefono2() : ""));
/* 163:179 */     reportParameters.put("usuarioImpresion", AppUtil.getUsuarioEnSesion().getNombreUsuario());
/* 164:180 */     reportParameters.put("identificacionOrganizacion", AppUtil.getOrganizacion().getIdentificacion());
/* 165:181 */     reportParameters.put("logoEmpresa", RutaArchivo.getUploadDir(AppUtil.getOrganizacion().getId(), "logo") + 
/* 166:182 */       AppUtil.getOrganizacion().getImagen());
/* 167:183 */     reportParameters.put("p_formatoFecha", getFormatoFecha());
/* 168:184 */     reportParameters.put("p_indicadorMatriz", Boolean.valueOf(AppUtil.getSucursal().isIndicadorMatriz()));
/* 169:185 */     if (this.exportOption == ExportOption.EXCEL) {
/* 170:186 */       reportParameters.put("IS_IGNORE_PAGINATION", Boolean.valueOf(true));
/* 171:    */     }
/* 172:190 */     if (AppUtil.getSucursal().getCiudad() != null) {
/* 173:191 */       reportParameters.put("ciudad", sucursal.getCiudad().getNombre());
/* 174:    */     } else {
/* 175:193 */       reportParameters.put("ciudad", getLanguageController().getMensaje("msg_no_parametrizado"));
/* 176:    */     }
/* 177:195 */     reportParameters.put("p_formatoDinero", getFormatoDinero());
/* 178:    */     
/* 179:197 */     return reportParameters;
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected String getCompileDir()
/* 183:    */   {
/* 184:201 */     return "/reportes/";
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected abstract JRDataSource getJRDataSource();
/* 188:    */   
/* 189:    */   protected abstract String getCompileFileName();
/* 190:    */   
/* 191:    */   public List<SelectItem> getListaExportOption()
/* 192:    */   {
/* 193:209 */     if (this.listaExportOption == null)
/* 194:    */     {
/* 195:210 */       this.listaExportOption = new ArrayList();
/* 196:211 */       for (ExportOption exportOption : ExportOption.values()) {
/* 197:212 */         if (exportOption != ExportOption.PRINTER)
/* 198:    */         {
/* 199:213 */           SelectItem item = new SelectItem(exportOption, exportOption.getNombre());
/* 200:214 */           this.listaExportOption.add(item);
/* 201:    */         }
/* 202:    */       }
/* 203:    */     }
/* 204:218 */     return this.listaExportOption;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public abstract String execute();
/* 208:    */   
/* 209:    */   public boolean isDocumentoElectronico()
/* 210:    */   {
/* 211:224 */     return this.documentoElectronico;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setDocumentoElectronico(boolean documentoElectronico)
/* 215:    */   {
/* 216:228 */     this.documentoElectronico = documentoElectronico;
/* 217:    */   }
/* 218:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.AbstractBaseReportBean
 * JD-Core Version:    0.7.0.1
 */