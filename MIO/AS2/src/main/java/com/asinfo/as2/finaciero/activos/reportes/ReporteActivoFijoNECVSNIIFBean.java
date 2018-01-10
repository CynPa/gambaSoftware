/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   5:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   8:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo;
/*   9:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteActivoFijo;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.model.SelectItem;
/*  21:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  22:    */ import net.sf.jasperreports.engine.JRException;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteActivoFijoNECVSNIIFBean
/*  28:    */   extends AbstractBaseReportBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 8436856997948409225L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioReporteActivoFijo servicioReporteActivoFijo;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioCategoriaActivo servicioCategoriaActivo;
/*  35:    */   private ActivoFijo activoFijo;
/*  36:    */   private CategoriaActivo categoriaActivo;
/*  37:    */   private List<SelectItem> listaSelectItemMes;
/*  38:    */   private boolean indicadorFechaCompra;
/*  39:    */   private int mes;
/*  40: 68 */   private int anio = 1;
/*  41: 69 */   private JRDataSource dataSource = null;
/*  42: 73 */   private final String COMPILE_FILE_NAME = "reporteActivoFijoNECVSNIIF";
/*  43:    */   
/*  44:    */   protected JRDataSource getJRDataSource()
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48: 83 */       List<Object[]> listaDatosReporte = new ArrayList();
/*  49: 84 */       listaDatosReporte = this.servicioReporteActivoFijo.getReporteActivoFijoNECVSNIIF(getAnio(), this.mes, this.categoriaActivo, this.activoFijo, 
/*  50: 85 */         AppUtil.getOrganizacion().getId(), isIndicadorFechaCompra());
/*  51: 87 */       if (listaDatosReporte.isEmpty()) {
/*  52: 88 */         addInfoMessage(getLanguageController().getMensaje("msg_no_hay_datos"));
/*  53:    */       }
/*  54: 90 */       String[] fields = { "codigoActivoFijo", "nombreActivoFijo", "fechaFacturaProveedor", "nombreCentroCosto", "nombreSubCategoriaActivo", "numeroFactura", "valorActivo", "valorMesDepreciado", "mesesDepreciados", "vidaUtil", "valorDepreciado", "valorResidual", "valorAdicional", "valorCompraRelacionada", "valorMesDepreciadoNIIF", "mesesDepreciadosNIIF", "vidaUtilNIIF", "valorDepreciadoNIIF", "valorResidualNIIF", "nombreCategoriaActivo", "valorFiscal", "valorNIIF", "depresionActual", "depresionActualNIIF", "vidaUtilFiscalInformativa", "vidaUtilNIIFInformativa" };
/*  55:    */       
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59: 95 */       this.dataSource = new QueryResultDataSource(listaDatosReporte, fields);
/*  60:    */     }
/*  61:    */     catch (Exception e)
/*  62:    */     {
/*  63: 97 */       e.printStackTrace();
/*  64:    */     }
/*  65: 99 */     return this.dataSource;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected String getCompileFileName()
/*  69:    */   {
/*  70:108 */     return "reporteActivoFijoNECVSNIIF";
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected Map<String, Object> getReportParameters()
/*  74:    */   {
/*  75:119 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  76:120 */     reportParameters.put("ReportTitle", "Activo Fijo NEC vs NIIF");
/*  77:121 */     reportParameters.put("anio", Integer.valueOf(this.anio));
/*  78:122 */     reportParameters.put("mes", Integer.valueOf(this.mes));
/*  79:123 */     reportParameters.put("categoriaActivo", this.categoriaActivo == null ? null : this.categoriaActivo.getNombre());
/*  80:124 */     reportParameters.put("activoFijo", this.activoFijo == null ? null : this.activoFijo.getNombre());
/*  81:125 */     reportParameters.put("SUBREPORT_DIR", getPathReportes());
/*  82:126 */     reportParameters.put("lista", getJRDataSource());
/*  83:127 */     return reportParameters;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String execute()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:137 */       super.prepareReport();
/*  91:    */     }
/*  92:    */     catch (JRException e)
/*  93:    */     {
/*  94:140 */       LOG.info("Error JRException");
/*  95:141 */       e.printStackTrace();
/*  96:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  97:    */     }
/*  98:    */     catch (IOException e)
/*  99:    */     {
/* 100:144 */       LOG.info("Error IOException");
/* 101:145 */       e.printStackTrace();
/* 102:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 103:    */     }
/* 104:149 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String cargarActivoFijo()
/* 108:    */   {
/* 109:153 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public ActivoFijo getActivoFijo()
/* 113:    */   {
/* 114:160 */     return this.activoFijo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setActivoFijo(ActivoFijo activoFijo)
/* 118:    */   {
/* 119:168 */     this.activoFijo = activoFijo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public CategoriaActivo getCategoriaActivo()
/* 123:    */   {
/* 124:177 */     return this.categoriaActivo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setCategoriaActivo(CategoriaActivo categoriaActivo)
/* 128:    */   {
/* 129:187 */     this.categoriaActivo = categoriaActivo;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getMes()
/* 133:    */   {
/* 134:196 */     return this.mes;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setMes(int mes)
/* 138:    */   {
/* 139:206 */     this.mes = mes;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getAnio()
/* 143:    */   {
/* 144:215 */     if (this.anio == 0) {
/* 145:216 */       this.anio = 1;
/* 146:    */     }
/* 147:218 */     return this.anio;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setAnio(int anio)
/* 151:    */   {
/* 152:228 */     this.anio = anio;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public boolean isIndicadorFechaCompra()
/* 156:    */   {
/* 157:237 */     return this.indicadorFechaCompra;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setIndicadorFechaCompra(boolean indicadorFechaCompra)
/* 161:    */   {
/* 162:247 */     this.indicadorFechaCompra = indicadorFechaCompra;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<SelectItem> getListaSelectItemMes()
/* 166:    */   {
/* 167:256 */     if (this.listaSelectItemMes == null)
/* 168:    */     {
/* 169:257 */       this.listaSelectItemMes = new ArrayList();
/* 170:258 */       for (Mes mes : Mes.values())
/* 171:    */       {
/* 172:259 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 173:260 */         this.listaSelectItemMes.add(selectItem);
/* 174:    */       }
/* 175:    */     }
/* 176:263 */     return this.listaSelectItemMes;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public List<CategoriaActivo> getListaCategoriaActivoFijo()
/* 180:    */   {
/* 181:272 */     List<CategoriaActivo> lista = new ArrayList();
/* 182:273 */     lista = this.servicioCategoriaActivo.obtenerListaCombo("nombre", true, null);
/* 183:274 */     return lista;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteActivoFijoNECVSNIIFBean
 * JD-Core Version:    0.7.0.1
 */