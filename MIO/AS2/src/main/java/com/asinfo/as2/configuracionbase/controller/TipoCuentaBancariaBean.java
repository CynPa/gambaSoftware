/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoCuentaBancaria;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.annotation.PostConstruct;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ import org.primefaces.component.datatable.DataTable;
/*  18:    */ import org.primefaces.model.LazyDataModel;
/*  19:    */ import org.primefaces.model.SortOrder;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class TipoCuentaBancariaBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 2741696197866454055L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioTipoCuentaBancaria servicioTipoCuentaBancaria;
/*  29:    */   private TipoCuentaBancaria tipoCuentaBancaria;
/*  30:    */   private LazyDataModel<TipoCuentaBancaria> listaTipoCuentaBancaria;
/*  31:    */   private DataTable dtTipoCuentaBancaria;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 66 */     this.listaTipoCuentaBancaria = new LazyDataModel()
/*  37:    */     {
/*  38:    */       private static final long serialVersionUID = 978604081320061255L;
/*  39:    */       
/*  40:    */       public List<TipoCuentaBancaria> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  41:    */       {
/*  42: 73 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43:    */         
/*  44: 75 */         List<TipoCuentaBancaria> lista = TipoCuentaBancariaBean.this.servicioTipoCuentaBancaria.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  45:    */         
/*  46:    */ 
/*  47: 78 */         TipoCuentaBancariaBean.this.listaTipoCuentaBancaria.setRowCount(TipoCuentaBancariaBean.this.servicioTipoCuentaBancaria.contarPorCriterio(filters));
/*  48: 79 */         return lista;
/*  49:    */       }
/*  50:    */     };
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void crearEntidad()
/*  54:    */   {
/*  55: 93 */     this.tipoCuentaBancaria = new TipoCuentaBancaria();
/*  56: 94 */     this.tipoCuentaBancaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  57: 95 */     this.tipoCuentaBancaria.setIdSucursal(AppUtil.getSucursal().getId());
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String editar()
/*  61:    */   {
/*  62:104 */     if ((getTipoCuentaBancaria() != null) && (getTipoCuentaBancaria().getIdTipoCuentaBancaria() > 0)) {
/*  63:105 */       setEditado(true);
/*  64:    */     } else {
/*  65:107 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  66:    */     }
/*  67:109 */     return "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String guardar()
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:119 */       this.servicioTipoCuentaBancaria.guardarTipoCuentaBancaria(this.tipoCuentaBancaria);
/*  75:120 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  76:121 */       setEditado(false);
/*  77:122 */       limpiar();
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  82:125 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  83:    */     }
/*  84:127 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String eliminar()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:137 */       this.servicioTipoCuentaBancaria.eliminarTipoCuentaBancaria(this.tipoCuentaBancaria);
/*  92:138 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:140 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  97:141 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  98:    */     }
/*  99:143 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String cargarDatos()
/* 103:    */   {
/* 104:152 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String limpiar()
/* 108:    */   {
/* 109:161 */     crearEntidad();
/* 110:162 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public TipoCuentaBancaria getTipoCuentaBancaria()
/* 114:    */   {
/* 115:178 */     return this.tipoCuentaBancaria;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setTipoCuentaBancaria(TipoCuentaBancaria tipoCuentaBancaria)
/* 119:    */   {
/* 120:188 */     this.tipoCuentaBancaria = tipoCuentaBancaria;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public LazyDataModel<TipoCuentaBancaria> getListaTipoCuentaBancaria()
/* 124:    */   {
/* 125:197 */     return this.listaTipoCuentaBancaria;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setListaTipoCuentaBancaria(LazyDataModel<TipoCuentaBancaria> listaTipoCuentaBancaria)
/* 129:    */   {
/* 130:207 */     this.listaTipoCuentaBancaria = listaTipoCuentaBancaria;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public DataTable getDtTipoCuentaBancaria()
/* 134:    */   {
/* 135:216 */     return this.dtTipoCuentaBancaria;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setDtTipoCuentaBancaria(DataTable dtTipoCuentaBancaria)
/* 139:    */   {
/* 140:226 */     this.dtTipoCuentaBancaria = dtTipoCuentaBancaria;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.TipoCuentaBancariaBean
 * JD-Core Version:    0.7.0.1
 */