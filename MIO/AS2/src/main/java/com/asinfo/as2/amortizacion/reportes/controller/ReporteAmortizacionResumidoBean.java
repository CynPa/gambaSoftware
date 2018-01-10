/*   1:    */ package com.asinfo.as2.amortizacion.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.amortizacion.procesos.servicio.ServicioAmortizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
/*   7:    */ import com.asinfo.as2.enumeraciones.Mes;
/*   8:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import javax.faces.model.SelectItem;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteAmortizacionResumidoBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1599721133141713459L;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioAmortizacion servicioAmortizacion;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioGenerico<TipoAmortizacion> servicioTipoAmortizacion;
/*  37:    */   private transient TipoAmortizacion tipoAmortizacion;
/*  38:    */   private transient List<SelectItem> listaSelectItemMes;
/*  39: 51 */   private transient int mes = FuncionesUtiles.getMes(new Date());
/*  40: 52 */   private transient int anio = FuncionesUtiles.obtenerAnioActual();
/*  41:    */   private transient List<TipoAmortizacion> listaTipoAmortizacion;
/*  42: 56 */   private final String COMPILE_FILE_NAME = "reporteAmortizacionResumido";
/*  43:    */   
/*  44:    */   protected JRDataSource getJRDataSource()
/*  45:    */   {
/*  46: 61 */     List listaDatosReporte = new ArrayList();
/*  47: 62 */     JRDataSource ds = null;
/*  48:    */     try
/*  49:    */     {
/*  50: 64 */       listaDatosReporte = this.servicioAmortizacion.getReporteAmortizacionResumido(getAnio(), this.mes, this.tipoAmortizacion, AppUtil.getOrganizacion()
/*  51: 65 */         .getId());
/*  52: 66 */       String[] fields = { "f_id_amortizacion", "f_tipo_amortizacion", "f_numero", "f_fecha_inicio_amortizacion", "f_meses_amortizados", "f_meses_por_amortizar", "f_valor", "f_valor_amortizado", "f_valor_amortizado_fecha", "f_valor_amortizado_mes", "f_proveedor", "f_numero_compra", "f_factura_proveedor", "f_fecha_compra", "f_descripcion" };
/*  53:    */       
/*  54:    */ 
/*  55: 69 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  56:    */     }
/*  57:    */     catch (Exception e)
/*  58:    */     {
/*  59: 71 */       LOG.info("Error " + e);
/*  60: 72 */       e.printStackTrace();
/*  61:    */     }
/*  62: 75 */     return ds;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected Map<String, Object> getReportParameters()
/*  66:    */   {
/*  67: 83 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  68: 84 */     reportParameters.put("ReportTitle", "Amortizacion");
/*  69: 85 */     reportParameters.put("p_anio", Integer.valueOf(getAnio()));
/*  70: 86 */     reportParameters.put("p_mes", Integer.valueOf(this.mes));
/*  71:    */     
/*  72: 88 */     return reportParameters;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected String getCompileFileName()
/*  76:    */   {
/*  77: 96 */     return "reporteAmortizacionResumido";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String execute()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:105 */       super.prepareReport();
/*  85:    */     }
/*  86:    */     catch (JRException e)
/*  87:    */     {
/*  88:107 */       LOG.info("Error JRException");
/*  89:108 */       e.printStackTrace();
/*  90:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  91:    */     }
/*  92:    */     catch (IOException e)
/*  93:    */     {
/*  94:111 */       LOG.info("Error IOException");
/*  95:112 */       e.printStackTrace();
/*  96:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  97:    */     }
/*  98:116 */     return null;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public TipoAmortizacion getTipoAmortizacion()
/* 102:    */   {
/* 103:123 */     return this.tipoAmortizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setTipoAmortizacion(TipoAmortizacion tipoAmortizacion)
/* 107:    */   {
/* 108:131 */     this.tipoAmortizacion = tipoAmortizacion;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<SelectItem> getListaSelectItemMes()
/* 112:    */   {
/* 113:138 */     if (this.listaSelectItemMes == null)
/* 114:    */     {
/* 115:139 */       this.listaSelectItemMes = new ArrayList();
/* 116:140 */       for (Mes mes : Mes.values())
/* 117:    */       {
/* 118:141 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 119:142 */         this.listaSelectItemMes.add(selectItem);
/* 120:    */       }
/* 121:    */     }
/* 122:146 */     return this.listaSelectItemMes;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setListaSelectItemMes(List<SelectItem> listaSelectItemMes)
/* 126:    */   {
/* 127:154 */     this.listaSelectItemMes = listaSelectItemMes;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getMes()
/* 131:    */   {
/* 132:161 */     return this.mes;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setMes(int mes)
/* 136:    */   {
/* 137:169 */     this.mes = mes;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getAnio()
/* 141:    */   {
/* 142:176 */     if (this.anio == 0) {
/* 143:177 */       this.anio = Calendar.getInstance().get(1);
/* 144:    */     }
/* 145:180 */     return this.anio;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setAnio(int anio)
/* 149:    */   {
/* 150:188 */     this.anio = anio;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<TipoAmortizacion> getListaTipoAmortizacion()
/* 154:    */   {
/* 155:195 */     if (null == this.listaTipoAmortizacion) {
/* 156:196 */       this.listaTipoAmortizacion = this.servicioTipoAmortizacion.obtenerListaCombo(TipoAmortizacion.class, "nombre", true, null);
/* 157:    */     }
/* 158:199 */     return this.listaTipoAmortizacion;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setListaTipoAmortizacion(List<TipoAmortizacion> listaTipoAmortizacion)
/* 162:    */   {
/* 163:207 */     this.listaTipoAmortizacion = listaTipoAmortizacion;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.amortizacion.reportes.controller.ReporteAmortizacionResumidoBean
 * JD-Core Version:    0.7.0.1
 */