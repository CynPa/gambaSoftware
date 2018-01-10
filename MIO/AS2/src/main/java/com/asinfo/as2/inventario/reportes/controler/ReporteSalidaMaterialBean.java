/*   1:    */ package com.asinfo.as2.inventario.reportes.controler;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.dao.produccion.OrdenSalidaMaterialDao;
/*   5:    */ import com.asinfo.as2.entities.OrdenSalidaMaterial;
/*   6:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*   7:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   8:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  17:    */ import net.sf.jasperreports.engine.JRException;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ 
/*  20:    */ @ManagedBean
/*  21:    */ @ViewScoped
/*  22:    */ public class ReporteSalidaMaterialBean
/*  23:    */   extends AbstractBaseReportBean
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 770222109067650882L;
/*  26:    */   @EJB
/*  27:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  28:    */   @EJB
/*  29:    */   OrdenSalidaMaterialDao ordenSalidaMaterialDao;
/*  30:    */   private OrdenSalidaMaterial ordenSalidaMaterial;
/*  31:    */   private OrdenSalidaMaterial devolucionOrdenSalidaMaterial;
/*  32:    */   private OrdenSalidaMaterial devolucionOrdenSalidaMaterialConsumoDirecto;
/*  33:    */   private int idOrdenFabricacion;
/*  34: 54 */   private final String COMPILE_FILE_NAME = "reporteOrdenSalidaMaterial";
/*  35: 55 */   private final String COMPILE_FILE_NAME_DEVOLUCION = "reporteDevolucionOrdenSalidaMaterial";
/*  36: 56 */   private final String COMPILE_FILE_NAME_DEVOLUCION_CONSUMO_DIRECTO = "reporteDevolucionOrdenSalidaMaterialConsumoDirecto";
/*  37: 57 */   private final String COMPILE_FILE_NAME_OFA = "reporteOrdenSalidaMaterialOFA";
/*  38:    */   
/*  39:    */   protected JRDataSource getJRDataSource()
/*  40:    */   {
/*  41: 62 */     List listaDatosReporte = new ArrayList();
/*  42: 63 */     JRDataSource ds = null;
/*  43: 64 */     String[] fields = null;
/*  44:    */     try
/*  45:    */     {
/*  46: 66 */       boolean devolucion = false;
/*  47: 67 */       int idOrden = 0;
/*  48:    */       
/*  49: 69 */       boolean devolucionConsumoDirecto = this.devolucionOrdenSalidaMaterialConsumoDirecto != null;
/*  50: 70 */       if (!devolucionConsumoDirecto)
/*  51:    */       {
/*  52: 71 */         devolucion = this.devolucionOrdenSalidaMaterial != null;
/*  53: 72 */         idOrden = devolucion ? getDevolucionOrdenSalidaMaterial().getId() : getOrdenSalidaMaterial().getId();
/*  54:    */       }
/*  55:    */       else
/*  56:    */       {
/*  57: 75 */         idOrden = devolucionConsumoDirecto ? getDevolucionOrdenSalidaMaterialConsumoDirecto().getId() : getOrdenSalidaMaterial().getId();
/*  58:    */       }
/*  59: 79 */       if ((getOrdenSalidaMaterial() != null) && (getOrdenSalidaMaterial().isIndicadorImprimirOFA()))
/*  60:    */       {
/*  61: 81 */         listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteOrdenSalidaMaterialConOrdenFabricacion(idOrden, devolucion, null, null, null, null, false, devolucionConsumoDirecto);
/*  62:    */         
/*  63: 83 */         fields = new String[] { "f_numero", "f_fecha", "f_descripcionOrdenSalidaMaterial", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidadProducto", "f_cantidadDespachada", "f_cantidadADevolver", "f_cantidadDevuelta", "f_cantidadUtilizada", "f_descripcion", "f_OrdenFabricacion", "f_lote", "f_unidadInformativa", "f_cantidadInformativaDespacho", "f_cantidadInformativaDevolucion", "f_cantidadDesecho", "f_cantidadAdicional", "f_destinoCosto", "f_cantidadDOSMOSF", "f_cantidadUtilizadaDOSMOSF", "f_cantidadDesechoDOSMOSF" };
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67: 91 */         listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteOrdenSalidaMaterial(idOrden, devolucion, null, null, null, null, false, devolucionConsumoDirecto);
/*  68:    */         
/*  69:    */ 
/*  70: 94 */         fields = new String[] { "f_idDetalleOrdenSalidaMaterial", "f_numero", "f_fecha", "f_descripcionOrdenSalidaMaterial", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidadProducto", "f_cantidadDespachada", "f_cantidadADevolver", "f_cantidadDevuelta", "f_cantidadUtilizada", "f_descripcion", "f_OrdenFabricacion", "f_lote", "f_unidadInformativa", "f_cantidadInformativaDespacho", "f_cantidadInformativaDevolucion", "f_cantidadDesecho", "f_cantidadAdicional", "f_destinoCosto" };
/*  71:    */       }
/*  72:101 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76:103 */       e.printStackTrace();
/*  77:    */     }
/*  78:105 */     return ds;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected String getCompileFileName()
/*  82:    */   {
/*  83:110 */     if (this.devolucionOrdenSalidaMaterial != null) {
/*  84:111 */       return "reporteDevolucionOrdenSalidaMaterial";
/*  85:    */     }
/*  86:113 */     if (this.devolucionOrdenSalidaMaterialConsumoDirecto != null) {
/*  87:114 */       return "reporteDevolucionOrdenSalidaMaterialConsumoDirecto";
/*  88:    */     }
/*  89:117 */     if ((getOrdenSalidaMaterial() != null) && (getOrdenSalidaMaterial().isIndicadorImprimirOFA())) {
/*  90:118 */       return "reporteOrdenSalidaMaterialOFA";
/*  91:    */     }
/*  92:120 */     return "reporteOrdenSalidaMaterial";
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected Map<String, Object> getReportParameters()
/*  96:    */   {
/*  97:129 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  98:130 */     if (this.ordenSalidaMaterial != null) {
/*  99:131 */       reportParameters.put("ReportTitle", "Orden Salida de Material");
/* 100:132 */     } else if (this.devolucionOrdenSalidaMaterial != null) {
/* 101:133 */       reportParameters.put("ReportTitle", "Devolucion Orden Salida de Material");
/* 102:    */     }
/* 103:135 */     return reportParameters;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String execute()
/* 107:    */   {
/* 108:142 */     if (getOrdenSalidaMaterial().getId() == 0) {
/* 109:143 */       addErrorMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 110:    */     } else {
/* 111:    */       try
/* 112:    */       {
/* 113:146 */         super.prepareReport();
/* 114:147 */         this.devolucionOrdenSalidaMaterial = null;
/* 115:148 */         this.ordenSalidaMaterial = null;
/* 116:    */       }
/* 117:    */       catch (JRException e)
/* 118:    */       {
/* 119:150 */         LOG.info("Error JRException");
/* 120:151 */         e.printStackTrace();
/* 121:152 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 122:    */       }
/* 123:    */       catch (IOException e)
/* 124:    */       {
/* 125:154 */         LOG.info("Error IOException");
/* 126:155 */         e.printStackTrace();
/* 127:156 */         addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 128:    */       }
/* 129:    */     }
/* 130:159 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public OrdenSalidaMaterial getOrdenSalidaMaterial()
/* 134:    */   {
/* 135:163 */     if (this.ordenSalidaMaterial == null) {
/* 136:164 */       this.ordenSalidaMaterial = new OrdenSalidaMaterial();
/* 137:    */     }
/* 138:166 */     return this.ordenSalidaMaterial;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setOrdenSalidaMaterial(OrdenSalidaMaterial ordenSalidaMaterial)
/* 142:    */   {
/* 143:170 */     this.ordenSalidaMaterial = ordenSalidaMaterial;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getIdOrdenFabricacion()
/* 147:    */   {
/* 148:174 */     return this.idOrdenFabricacion;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setIdOrdenFabricacion(int idOrdenFabricacion)
/* 152:    */   {
/* 153:178 */     this.idOrdenFabricacion = idOrdenFabricacion;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public OrdenSalidaMaterial getDevolucionOrdenSalidaMaterial()
/* 157:    */   {
/* 158:182 */     return this.devolucionOrdenSalidaMaterial;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setDevolucionOrdenSalidaMaterial(OrdenSalidaMaterial devolucionOrdenSalidaMaterial)
/* 162:    */   {
/* 163:186 */     this.devolucionOrdenSalidaMaterial = devolucionOrdenSalidaMaterial;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public OrdenSalidaMaterial getDevolucionOrdenSalidaMaterialConsumoDirecto()
/* 167:    */   {
/* 168:193 */     return this.devolucionOrdenSalidaMaterialConsumoDirecto;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setDevolucionOrdenSalidaMaterialConsumoDirecto(OrdenSalidaMaterial devolucionOrdenSalidaMaterialConsumoDirecto)
/* 172:    */   {
/* 173:200 */     this.devolucionOrdenSalidaMaterialConsumoDirecto = devolucionOrdenSalidaMaterialConsumoDirecto;
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controler.ReporteSalidaMaterialBean
 * JD-Core Version:    0.7.0.1
 */