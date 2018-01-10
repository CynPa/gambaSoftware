/*   1:    */ package com.asinfo.as2.finaciero.activos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*   7:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo;
/*   8:    */ import com.asinfo.as2.financiero.activos.reportes.servicio.ServicioReporteActivoFijo;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import net.sf.jasperreports.engine.JRException;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ReporteActivoFijoFechasBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioCategoriaActivo servicioCategoriaActivo;
/*  36:    */   @EJB
/*  37:    */   private ServicioReporteActivoFijo servicioReporteActivoFijo;
/*  38:    */   private Date fechaDesde;
/*  39:    */   private Date fechaHasta;
/*  40:    */   private CategoriaActivo categoriaActivo;
/*  41:    */   private SubcategoriaActivo subcategoriaActivo;
/*  42:    */   private List<CategoriaActivo> listaCategoriaActivo;
/*  43:    */   private List<SubcategoriaActivo> listaSubcategoriaActivo;
/*  44: 63 */   private boolean activo = true;
/*  45:    */   
/*  46:    */   protected JRDataSource getJRDataSource()
/*  47:    */   {
/*  48: 68 */     List<Object[]> listaReporte = new ArrayList();
/*  49: 69 */     JRDataSource ds = null;
/*  50:    */     try
/*  51:    */     {
/*  52: 72 */       listaReporte = this.servicioReporteActivoFijo.listaActivoFijoFechas(this.fechaDesde, this.fechaHasta, this.categoriaActivo, this.subcategoriaActivo, 
/*  53: 73 */         AppUtil.getOrganizacion().getIdOrganizacion(), isActivo());
/*  54: 74 */       String[] fields = { "f_codigo", "f_nombre", "f_facturaProveedor", "f_valorActivo", "f_valorCompraRelacionada", "f_valorAdicional", "f_fechaActivoFijoProveedor", "categoria", "sub_categoria" };
/*  55:    */       
/*  56: 76 */       ds = new QueryResultDataSource(listaReporte, fields);
/*  57:    */     }
/*  58:    */     catch (Exception e)
/*  59:    */     {
/*  60: 78 */       e.printStackTrace();
/*  61:    */     }
/*  62: 81 */     return ds;
/*  63:    */   }
/*  64:    */   
/*  65:    */   @PostConstruct
/*  66:    */   public void init()
/*  67:    */   {
/*  68: 86 */     Calendar calfechaDesde = Calendar.getInstance();
/*  69: 87 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  70: 88 */     this.fechaDesde = calfechaDesde.getTime();
/*  71: 89 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected String getCompileFileName()
/*  75:    */   {
/*  76: 95 */     return "reporteActivoFijoFechas";
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String execute()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:102 */       super.prepareReport();
/*  84:    */     }
/*  85:    */     catch (JRException e)
/*  86:    */     {
/*  87:105 */       LOG.info("Error JRException");
/*  88:106 */       e.printStackTrace();
/*  89:107 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  90:    */     }
/*  91:    */     catch (IOException e)
/*  92:    */     {
/*  93:109 */       LOG.info("Error IOException");
/*  94:110 */       e.printStackTrace();
/*  95:111 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  96:    */     }
/*  97:114 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void cargarListaSubcategoriaActivo()
/* 101:    */   {
/* 102:118 */     this.listaSubcategoriaActivo = this.servicioReporteActivoFijo.listaSubcategoriaActivo(this.categoriaActivo.getId());
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected Map<String, Object> getReportParameters()
/* 106:    */   {
/* 107:123 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 108:124 */     reportParameters.put("ReportTitle", "Activo Fijo");
/* 109:125 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 110:126 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 111:127 */     reportParameters.put("p_categoria", this.categoriaActivo != null ? this.categoriaActivo.getNombre() : "Todos");
/* 112:128 */     reportParameters.put("p_subCategoria", this.subcategoriaActivo != null ? this.subcategoriaActivo.getNombre() : "Todos");
/* 113:129 */     return reportParameters;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Date getFechaDesde()
/* 117:    */   {
/* 118:136 */     return this.fechaDesde;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setFechaDesde(Date fechaDesde)
/* 122:    */   {
/* 123:144 */     this.fechaDesde = fechaDesde;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Date getFechaHasta()
/* 127:    */   {
/* 128:151 */     return this.fechaHasta;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setFechaHasta(Date fechaHasta)
/* 132:    */   {
/* 133:159 */     this.fechaHasta = fechaHasta;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public CategoriaActivo getCategoriaActivo()
/* 137:    */   {
/* 138:166 */     return this.categoriaActivo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setCategoriaActivo(CategoriaActivo categoriaActivo)
/* 142:    */   {
/* 143:174 */     this.categoriaActivo = categoriaActivo;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public SubcategoriaActivo getSubcategoriaActivo()
/* 147:    */   {
/* 148:181 */     return this.subcategoriaActivo;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setSubcategoriaActivo(SubcategoriaActivo subcategoriaActivo)
/* 152:    */   {
/* 153:189 */     this.subcategoriaActivo = subcategoriaActivo;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<CategoriaActivo> getListaCategoriaActivo()
/* 157:    */   {
/* 158:196 */     HashMap<String, String> filters = new HashMap();
/* 159:197 */     filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 160:198 */     if (this.listaCategoriaActivo == null) {
/* 161:199 */       this.listaCategoriaActivo = this.servicioCategoriaActivo.obtenerListaCombo("nombre", true, filters);
/* 162:    */     }
/* 163:201 */     return this.listaCategoriaActivo;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setListaCategoriaActivo(List<CategoriaActivo> listaCategoriaActivo)
/* 167:    */   {
/* 168:209 */     this.listaCategoriaActivo = listaCategoriaActivo;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<SubcategoriaActivo> getListaSubcategoriaActivo()
/* 172:    */   {
/* 173:216 */     return this.listaSubcategoriaActivo;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setListaSubcategoriaActivo(List<SubcategoriaActivo> listaSubcategoriaActivo)
/* 177:    */   {
/* 178:224 */     this.listaSubcategoriaActivo = listaSubcategoriaActivo;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isActivo()
/* 182:    */   {
/* 183:228 */     return this.activo;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setActivo(boolean activo)
/* 187:    */   {
/* 188:232 */     this.activo = activo;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.reportes.ReporteActivoFijoFechasBean
 * JD-Core Version:    0.7.0.1
 */