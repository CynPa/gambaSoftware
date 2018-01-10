/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioParroquia;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Cliente;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  13:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*  14:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  15:    */ import java.math.BigDecimal;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.event.RowEditEvent;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class ActualizarClienteBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 5928599170604257612L;
/*  36:    */   @EJB
/*  37:    */   protected ServicioEmpresa servicioEmpresa;
/*  38:    */   @EJB
/*  39:    */   private transient ServicioParroquia servicioParroquia;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*  42:    */   private DataTable dtEmpresa;
/*  43:    */   private Empresa empresa;
/*  44:    */   private LazyDataModel<Empresa> listaEmpresa;
/*  45:    */   
/*  46:    */   @PostConstruct
/*  47:    */   public void init()
/*  48:    */   {
/*  49: 73 */     this.listaEmpresa = new LazyDataModel()
/*  50:    */     {
/*  51:    */       private static final long serialVersionUID = 1L;
/*  52:    */       
/*  53:    */       public List<Empresa> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  54:    */       {
/*  55: 81 */         List<Empresa> lista = new ArrayList();
/*  56:    */         
/*  57: 83 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  58: 84 */         filters.put("indicadorCliente", "true");
/*  59: 85 */         lista = ActualizarClienteBean.this.servicioEmpresa.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  60: 86 */         ActualizarClienteBean.this.listaEmpresa.setRowCount(ActualizarClienteBean.this.servicioEmpresa.contarPorCriterio(filters));
/*  61:    */         
/*  62:    */ 
/*  63: 89 */         Date hoy = new Date();
/*  64: 90 */         for (Empresa empresa : lista) {
/*  65: 91 */           if (empresa.getCliente() != null)
/*  66:    */           {
/*  67: 93 */             BigDecimal porcentajeMorosidad = ActualizarClienteBean.this.servicioVerificadorVentas.getPorcentajeMorosidad(empresa.getId(), hoy);
/*  68: 94 */             empresa.getCliente().setPorcentajeMorosidad(porcentajeMorosidad);
/*  69:    */             try
/*  70:    */             {
/*  71: 97 */               ActualizarClienteBean.this.servicioVerificadorVentas.getEmpresaDocumentacionCompleta(empresa.getCliente(), hoy);
/*  72: 98 */               empresa.getCliente().setIndicadorCumpleDocumentacion(Boolean.valueOf(true));
/*  73:    */             }
/*  74:    */             catch (ExcepcionAS2Ventas e)
/*  75:    */             {
/*  76:100 */               empresa.getCliente().setIndicadorCumpleDocumentacion(Boolean.valueOf(false));
/*  77:    */             }
/*  78:104 */             Integer numeroDocumentosSinGarantia = ActualizarClienteBean.this.servicioVerificadorVentas.getNumeroFacturasPendientesSinGarantia(empresa.getCliente());
/*  79:105 */             empresa.getCliente().setCantidadFacturasPendientesSinGarantia(numeroDocumentosSinGarantia);
/*  80:    */           }
/*  81:    */         }
/*  82:108 */         return lista;
/*  83:    */       }
/*  84:    */     };
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void onRowEdit(RowEditEvent event)
/*  88:    */   {
/*  89:114 */     ((Empresa)event.getObject()).getId();
/*  90:    */     try
/*  91:    */     {
/*  92:116 */       this.servicioEmpresa.guardarActualizacionCliente(getEmpresa());
/*  93:    */     }
/*  94:    */     catch (AS2Exception e)
/*  95:    */     {
/*  96:118 */       JsfUtil.addErrorMessage(e, "");
/*  97:119 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  98:    */     }
/*  99:    */     catch (ExcepcionAS2Identification e)
/* 100:    */     {
/* 101:121 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 102:122 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 103:    */     }
/* 104:    */     catch (ExcepcionAS2 e)
/* 105:    */     {
/* 106:124 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 107:125 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 112:128 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void onRowCancel(RowEditEvent event)
/* 117:    */   {
/* 118:133 */     ((Empresa)event.getObject()).getId();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public DataTable getDtEmpresa()
/* 122:    */   {
/* 123:137 */     return this.dtEmpresa;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setDtEmpresa(DataTable dtEmpresa)
/* 127:    */   {
/* 128:141 */     this.dtEmpresa = dtEmpresa;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Empresa getEmpresa()
/* 132:    */   {
/* 133:145 */     return this.empresa;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setEmpresa(Empresa empresa)
/* 137:    */   {
/* 138:149 */     this.empresa = empresa;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public LazyDataModel<Empresa> getListaEmpresa()
/* 142:    */   {
/* 143:153 */     return this.listaEmpresa;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setListaEmpresa(LazyDataModel<Empresa> listaEmpresa)
/* 147:    */   {
/* 148:157 */     this.listaEmpresa = listaEmpresa;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String editar()
/* 152:    */   {
/* 153:163 */     return null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String guardar()
/* 157:    */   {
/* 158:169 */     return null;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String eliminar()
/* 162:    */   {
/* 163:175 */     return null;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String limpiar()
/* 167:    */   {
/* 168:181 */     return null;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String cargarDatos()
/* 172:    */   {
/* 173:186 */     return null;
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.ActualizarClienteBean
 * JD-Core Version:    0.7.0.1
 */