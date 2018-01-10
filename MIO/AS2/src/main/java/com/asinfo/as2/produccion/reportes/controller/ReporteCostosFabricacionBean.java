/*   1:    */ package com.asinfo.as2.produccion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.Producto;
/*   6:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   7:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioCostosDeFabricacion;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  10:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  11:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
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
/*  27:    */ public class ReporteCostosFabricacionBean
/*  28:    */   extends AbstractBaseReportBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 605465445550205775L;
/*  31:    */   @EJB
/*  32:    */   private ServicioCostosDeFabricacion servicioCostosDeFabricacion;
/*  33: 58 */   private int anioDesde = FuncionesUtiles.getAnio(new Date());
/*  34: 59 */   private int mesDesde = FuncionesUtiles.getMes(new Date());
/*  35: 60 */   private int anioHasta = FuncionesUtiles.getAnio(new Date());
/*  36: 61 */   private int mesHasta = FuncionesUtiles.getMes(new Date());
/*  37:    */   private Producto producto;
/*  38:    */   private boolean indicadorResumen;
/*  39:    */   private List<SelectItem> listaSelectItemMes;
/*  40:    */   
/*  41:    */   protected JRDataSource getJRDataSource()
/*  42:    */   {
/*  43: 78 */     List listaDatosReporte = new ArrayList();
/*  44: 79 */     JRDataSource ds = null;
/*  45:    */     try
/*  46:    */     {
/*  47: 82 */       listaDatosReporte = this.servicioCostosDeFabricacion.getReporteCostosFabricacion(AppUtil.getOrganizacion().getId(), this.producto, this.anioDesde, this.mesDesde, this.anioHasta, this.mesHasta);
/*  48:    */       
/*  49: 84 */       String[] fields = { "codigoProducto", "nombreProducto", "anio", "mes", "codigoRutaFabricacion", "nombreRutaFabricacion", "numeroOrdenFabricacion", "fechaOrdenFabricacion", "fechaLanzamientoOrdenFabricacion", "fechaCierreOrdenFabricacion", "cantidadTotal", "cantidadFabricadaTotal", "cantidadFabricadaMes", "costoMaterialesInicial", "costoManoObraInicial", "costoIndirectosInicial", "costoDepreciacionInicial", "costoMaterialesMes", "costoManoObraMes", "costoIndirectosMes", "costoDepreciacionMes", "costoMaterialesAsignadoMes", "costoManoObraAsignadoMes", "costoIndirectosAsignadoMes", "costoDepreciacionAsignadoMes", "costoMaterialesPendiente", "costoManoObraPendiente", "costoIndirectosPendiente", "costoDepreciacionPendiente" };
/*  50:    */       
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57: 92 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61: 95 */       LOG.info("Error " + e);
/*  62: 96 */       e.printStackTrace();
/*  63:    */     }
/*  64: 98 */     return ds;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected String getCompileFileName()
/*  68:    */   {
/*  69:108 */     if (this.indicadorResumen) {
/*  70:109 */       return "reporteCostosFabricacionResumido";
/*  71:    */     }
/*  72:111 */     return "reporteCostosFabricacion";
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected Map<String, Object> getReportParameters()
/*  76:    */   {
/*  77:122 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  78:123 */     reportParameters.put("anioDesde", Integer.valueOf(getAnioDesde()));
/*  79:124 */     reportParameters.put("mesDesde", Integer.valueOf(getMesDesde()));
/*  80:125 */     reportParameters.put("anioHasta", Integer.valueOf(getAnioHasta()));
/*  81:126 */     reportParameters.put("mesHasta", Integer.valueOf(getMesHasta()));
/*  82:127 */     if (!this.indicadorResumen) {
/*  83:128 */       reportParameters.put("ReportTitle", "Reporte Costos de Fabricacion");
/*  84:    */     } else {
/*  85:130 */       reportParameters.put("ReportTitle", "Reporte Costos de Fabricacion");
/*  86:    */     }
/*  87:132 */     return reportParameters;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String execute()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:142 */       super.prepareReport();
/*  95:    */     }
/*  96:    */     catch (JRException e)
/*  97:    */     {
/*  98:144 */       LOG.info("Error JRException");
/*  99:145 */       e.printStackTrace();
/* 100:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 101:    */     }
/* 102:    */     catch (IOException e)
/* 103:    */     {
/* 104:148 */       LOG.info("Error IOException");
/* 105:149 */       e.printStackTrace();
/* 106:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 107:    */     }
/* 108:153 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void cargarProducto(Producto producto)
/* 112:    */   {
/* 113:157 */     this.producto = producto;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<SelectItem> getListaSelectItemMes()
/* 117:    */   {
/* 118:166 */     if (this.listaSelectItemMes == null)
/* 119:    */     {
/* 120:167 */       this.listaSelectItemMes = new ArrayList();
/* 121:168 */       for (Mes mes : Mes.values())
/* 122:    */       {
/* 123:169 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 124:170 */         this.listaSelectItemMes.add(selectItem);
/* 125:    */       }
/* 126:    */     }
/* 127:173 */     return this.listaSelectItemMes;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getAnioDesde()
/* 131:    */   {
/* 132:177 */     return this.anioDesde;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setAnioDesde(int anioDesde)
/* 136:    */   {
/* 137:181 */     this.anioDesde = anioDesde;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getMesDesde()
/* 141:    */   {
/* 142:185 */     return this.mesDesde;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setMesDesde(int mesDesde)
/* 146:    */   {
/* 147:189 */     this.mesDesde = mesDesde;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getAnioHasta()
/* 151:    */   {
/* 152:193 */     return this.anioHasta;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setAnioHasta(int anioHasta)
/* 156:    */   {
/* 157:197 */     this.anioHasta = anioHasta;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getMesHasta()
/* 161:    */   {
/* 162:201 */     return this.mesHasta;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setMesHasta(int mesHasta)
/* 166:    */   {
/* 167:205 */     this.mesHasta = mesHasta;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Producto getProducto()
/* 171:    */   {
/* 172:209 */     return this.producto;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setProducto(Producto producto)
/* 176:    */   {
/* 177:213 */     this.producto = producto;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isIndicadorResumen()
/* 181:    */   {
/* 182:217 */     return this.indicadorResumen;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 186:    */   {
/* 187:221 */     this.indicadorResumen = indicadorResumen;
/* 188:    */   }
/* 189:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.controller.ReporteCostosFabricacionBean
 * JD-Core Version:    0.7.0.1
 */