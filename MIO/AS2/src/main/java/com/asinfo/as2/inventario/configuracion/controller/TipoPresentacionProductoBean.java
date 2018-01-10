/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.TipoPresentacionProducto;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class TipoPresentacionProductoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<TipoPresentacionProducto> servicioTipoPresentacionProducto;
/*  32:    */   private TipoPresentacionProducto tipoPresentacionProducto;
/*  33:    */   private LazyDataModel<TipoPresentacionProducto> listaTipoPresentacionProducto;
/*  34:    */   private DataTable dataTableTipoPresentacionProducto;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 69 */     this.listaTipoPresentacionProducto = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  42:    */       
/*  43:    */       public List<TipoPresentacionProducto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 76 */         TipoPresentacionProductoBean.this.agregarFiltroOrganizacion(filters);
/*  46: 77 */         List<TipoPresentacionProducto> lista = new ArrayList();
/*  47: 78 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  48:    */         try
/*  49:    */         {
/*  50: 80 */           lista = TipoPresentacionProductoBean.this.servicioTipoPresentacionProducto.obtenerListaPorPagina(TipoPresentacionProducto.class, startIndex, pageSize, sortField, ordenar, filters);
/*  51:    */         }
/*  52:    */         catch (Exception e)
/*  53:    */         {
/*  54: 84 */           e.printStackTrace();
/*  55:    */         }
/*  56: 87 */         TipoPresentacionProductoBean.this.listaTipoPresentacionProducto.setRowCount(TipoPresentacionProductoBean.this.servicioTipoPresentacionProducto.contarPorCriterio(TipoPresentacionProducto.class, filters));
/*  57: 88 */         return lista;
/*  58:    */       }
/*  59:    */     };
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String editar()
/*  63:    */   {
/*  64:101 */     if (getTipoPresentacionProducto().getIdTipoPresentacionProducto() > 0) {
/*  65:102 */       setEditado(true);
/*  66:    */     } else {
/*  67:104 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  68:    */     }
/*  69:107 */     return "";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String guardar()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:118 */       this.servicioTipoPresentacionProducto.guardarValidar(this.tipoPresentacionProducto);
/*  77:119 */       cargarDatos();
/*  78:    */       
/*  79:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  80:    */     }
/*  81:    */     catch (AS2Exception e)
/*  82:    */     {
/*  83:123 */       JsfUtil.addErrorMessage(e, "");
/*  84:    */     }
/*  85:    */     catch (Exception e)
/*  86:    */     {
/*  87:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  88:126 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  89:    */     }
/*  90:128 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String eliminar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:139 */       this.servicioTipoPresentacionProducto.eliminar(this.tipoPresentacionProducto);
/*  98:140 */       cargarDatos();
/*  99:    */       
/* 100:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 101:    */     }
/* 102:    */     catch (Exception e)
/* 103:    */     {
/* 104:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:145 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 106:    */     }
/* 107:147 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String cargarDatos()
/* 111:    */   {
/* 112:158 */     setEditado(false);
/* 113:    */     try
/* 114:    */     {
/* 115:161 */       limpiar();
/* 116:    */     }
/* 117:    */     catch (Exception e)
/* 118:    */     {
/* 119:164 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 120:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 121:    */     }
/* 122:167 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String limpiar()
/* 126:    */   {
/* 127:177 */     this.tipoPresentacionProducto = new TipoPresentacionProducto();
/* 128:178 */     this.tipoPresentacionProducto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 129:179 */     this.tipoPresentacionProducto.setIdSucursal(AppUtil.getSucursal().getId());
/* 130:180 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public TipoPresentacionProducto getTipoPresentacionProducto()
/* 134:    */   {
/* 135:189 */     return this.tipoPresentacionProducto;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setTipoPresentacionProducto(TipoPresentacionProducto tipoPresentacionProducto)
/* 139:    */   {
/* 140:199 */     this.tipoPresentacionProducto = tipoPresentacionProducto;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public DataTable getDataTableTipoPresentacionProducto()
/* 144:    */   {
/* 145:208 */     return this.dataTableTipoPresentacionProducto;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setDataTableTipoPresentacionProducto(DataTable dataTableTipoPresentacionProducto)
/* 149:    */   {
/* 150:212 */     this.dataTableTipoPresentacionProducto = dataTableTipoPresentacionProducto;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public LazyDataModel<TipoPresentacionProducto> getListaTipoPresentacionProducto()
/* 154:    */   {
/* 155:216 */     if (this.listaTipoPresentacionProducto == null) {
/* 156:217 */       cargarDatos();
/* 157:    */     }
/* 158:219 */     return this.listaTipoPresentacionProducto;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setListaTipoPresentacionProducto(LazyDataModel<TipoPresentacionProducto> listaTipoPresentacionProducto)
/* 162:    */   {
/* 163:223 */     this.listaTipoPresentacionProducto = listaTipoPresentacionProducto;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.TipoPresentacionProductoBean
 * JD-Core Version:    0.7.0.1
 */