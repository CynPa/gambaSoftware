/*   1:    */ package com.asinfo.as2.finaciero.cobros.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.TipoTarjetaCredito;
/*   8:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.event.SelectEvent;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class TipoTarjetaCreditoBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -9061218854698018535L;
/*  29:    */   @EJB
/*  30:    */   private ServicioGenerico<TipoTarjetaCredito> servicioTipoTarjetaCredito;
/*  31:    */   private TipoTarjetaCredito tipoTarjetaCredito;
/*  32:    */   private LazyDataModel<TipoTarjetaCredito> listaTipoTarjetaCredito;
/*  33:    */   private DataTable dataTablePlan;
/*  34:    */   
/*  35:    */   @PostConstruct
/*  36:    */   public void init()
/*  37:    */   {
/*  38: 59 */     this.listaTipoTarjetaCredito = new LazyDataModel()
/*  39:    */     {
/*  40:    */       private static final long serialVersionUID = 1L;
/*  41:    */       
/*  42:    */       public List<TipoTarjetaCredito> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  43:    */       {
/*  44: 66 */         List<TipoTarjetaCredito> lista = new ArrayList();
/*  45: 67 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  46: 68 */         lista = TipoTarjetaCreditoBean.this.servicioTipoTarjetaCredito.obtenerListaPorPagina(TipoTarjetaCredito.class, startIndex, pageSize, sortField, ordenar, filters);
/*  47: 69 */         TipoTarjetaCreditoBean.this.listaTipoTarjetaCredito.setRowCount(TipoTarjetaCreditoBean.this.servicioTipoTarjetaCredito.contarPorCriterio(TipoTarjetaCredito.class, filters));
/*  48:    */         
/*  49: 71 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String editar()
/*  55:    */   {
/*  56: 86 */     if (this.tipoTarjetaCredito.getIdTipoTarjetaCredito() != 0)
/*  57:    */     {
/*  58: 87 */       List<String> listaCampos = new ArrayList();
/*  59:    */       
/*  60: 89 */       this.tipoTarjetaCredito = ((TipoTarjetaCredito)this.servicioTipoTarjetaCredito.cargarDetalle(TipoTarjetaCredito.class, this.tipoTarjetaCredito.getId(), listaCampos));
/*  61: 90 */       setEditado(true);
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 92 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  66:    */     }
/*  67: 95 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String guardar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:104 */       this.servicioTipoTarjetaCredito.guardar(this.tipoTarjetaCredito);
/*  75:105 */       limpiar();
/*  76:106 */       setEditado(false);
/*  77:107 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  82:110 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  83:    */     }
/*  84:113 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String eliminar()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:122 */       this.servicioTipoTarjetaCredito.eliminar(this.tipoTarjetaCredito);
/*  92:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  97:126 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  98:    */     }
/*  99:129 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String cargarDatos()
/* 103:    */   {
/* 104:137 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String limpiar()
/* 108:    */   {
/* 109:142 */     crearTipoTarjetaCredito();
/* 110:143 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void crearTipoTarjetaCredito()
/* 114:    */   {
/* 115:150 */     this.tipoTarjetaCredito = new TipoTarjetaCredito();
/* 116:151 */     this.tipoTarjetaCredito.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 117:152 */     this.tipoTarjetaCredito.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 118:153 */     this.tipoTarjetaCredito.setActivo(true);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void onRowSelect(SelectEvent event)
/* 122:    */   {
/* 123:160 */     TipoTarjetaCredito tipoTarjetaCredito = (TipoTarjetaCredito)event.getObject();
/* 124:161 */     setTipoTarjetaCredito(tipoTarjetaCredito);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public TipoTarjetaCredito getTipoTarjetaCredito()
/* 128:    */   {
/* 129:165 */     return this.tipoTarjetaCredito;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setTipoTarjetaCredito(TipoTarjetaCredito tipoTarjetaCredito)
/* 133:    */   {
/* 134:169 */     this.tipoTarjetaCredito = tipoTarjetaCredito;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public LazyDataModel<TipoTarjetaCredito> getListaTipoTarjetaCredito()
/* 138:    */   {
/* 139:173 */     return this.listaTipoTarjetaCredito;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setListaTipoTarjetaCredito(LazyDataModel<TipoTarjetaCredito> listaTipoTarjetaCredito)
/* 143:    */   {
/* 144:177 */     this.listaTipoTarjetaCredito = listaTipoTarjetaCredito;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public DataTable getDataTablePlan()
/* 148:    */   {
/* 149:181 */     return this.dataTablePlan;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDataTablePlan(DataTable dataTablePlan)
/* 153:    */   {
/* 154:185 */     this.dataTablePlan = dataTablePlan;
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.configuracion.TipoTarjetaCreditoBean
 * JD-Core Version:    0.7.0.1
 */