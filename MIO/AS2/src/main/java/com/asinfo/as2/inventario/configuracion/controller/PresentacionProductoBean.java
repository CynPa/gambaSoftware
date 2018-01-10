/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.PresentacionProducto;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.TipoPresentacionProducto;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.HashMap;
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
/*  26:    */ public class PresentacionProductoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1424377436906270962L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<PresentacionProducto> servicioPresentacionProducto;
/*  32:    */   @EJB
/*  33:    */   private ServicioGenerico<TipoPresentacionProducto> servicioTipoPresentacionProducto;
/*  34:    */   private PresentacionProducto presentacionProducto;
/*  35:    */   private LazyDataModel<PresentacionProducto> listaPresentacionProducto;
/*  36:    */   private List<TipoPresentacionProducto> listaTipoPresentacionProducto;
/*  37:    */   private DataTable dtPresentacionProducto;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 80 */     this.listaPresentacionProducto = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<PresentacionProducto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 87 */         List<PresentacionProducto> lista = new ArrayList();
/*  49: 88 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 90 */         PresentacionProductoBean.this.agregarFiltroOrganizacion(filters);
/*  52: 91 */         List<String> listaCampos = new ArrayList();
/*  53: 92 */         listaCampos.add("tipoPresentacionProducto");
/*  54: 93 */         lista = PresentacionProductoBean.this.servicioPresentacionProducto.obtenerListaPorPagina(PresentacionProducto.class, startIndex, pageSize, sortField, ordenar, filters, listaCampos);
/*  55:    */         
/*  56:    */ 
/*  57: 96 */         PresentacionProductoBean.this.listaPresentacionProducto.setRowCount(PresentacionProductoBean.this.servicioPresentacionProducto.contarPorCriterio(PresentacionProducto.class, filters));
/*  58:    */         
/*  59: 98 */         return lista;
/*  60:    */       }
/*  61:    */     };
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void crearEntidad()
/*  65:    */   {
/*  66:105 */     this.presentacionProducto = new PresentacionProducto();
/*  67:106 */     this.presentacionProducto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  68:107 */     this.presentacionProducto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  69:108 */     this.presentacionProducto.setActivo(true);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String editar()
/*  73:    */   {
/*  74:113 */     if (this.presentacionProducto.getId() != 0)
/*  75:    */     {
/*  76:114 */       List<String> listaCampos = new ArrayList();
/*  77:115 */       listaCampos.add("tipoPresentacionProducto");
/*  78:116 */       this.presentacionProducto = ((PresentacionProducto)this.servicioPresentacionProducto.cargarDetalle(PresentacionProducto.class, this.presentacionProducto.getId(), listaCampos));
/*  79:117 */       setEditado(true);
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:119 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  84:    */     }
/*  85:121 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String guardar()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:127 */       this.servicioPresentacionProducto.guardar(this.presentacionProducto);
/*  93:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  94:129 */       limpiar();
/*  95:130 */       setEditado(false);
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 100:133 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 101:    */     }
/* 102:135 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String eliminar()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:141 */       this.servicioPresentacionProducto.eliminar(this.presentacionProducto);
/* 110:142 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 115:145 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 116:    */     }
/* 117:147 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String cargarDatos()
/* 121:    */   {
/* 122:152 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String limpiar()
/* 126:    */   {
/* 127:157 */     crearEntidad();
/* 128:158 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public PresentacionProducto getPresentacionProducto()
/* 132:    */   {
/* 133:165 */     return this.presentacionProducto;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setPresentacionProducto(PresentacionProducto presentacionProducto)
/* 137:    */   {
/* 138:169 */     this.presentacionProducto = presentacionProducto;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public LazyDataModel<PresentacionProducto> getListaPresentacionProducto()
/* 142:    */   {
/* 143:173 */     return this.listaPresentacionProducto;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setListaPresentacionProducto(LazyDataModel<PresentacionProducto> listaPresentacionProducto)
/* 147:    */   {
/* 148:177 */     this.listaPresentacionProducto = listaPresentacionProducto;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public DataTable getDtPresentacionProducto()
/* 152:    */   {
/* 153:181 */     return this.dtPresentacionProducto;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setDtPresentacionProducto(DataTable dtPresentacionProducto)
/* 157:    */   {
/* 158:185 */     this.dtPresentacionProducto = dtPresentacionProducto;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<TipoPresentacionProducto> getListaTipoPresentacionProducto()
/* 162:    */   {
/* 163:189 */     if (this.listaTipoPresentacionProducto == null)
/* 164:    */     {
/* 165:190 */       Map<String, String> filtros = new HashMap();
/* 166:191 */       agregarFiltroOrganizacion(filtros);
/* 167:192 */       filtros.put("activo", "true");
/* 168:193 */       this.listaTipoPresentacionProducto = this.servicioTipoPresentacionProducto.obtenerListaCombo(TipoPresentacionProducto.class, "nombre", true, filtros);
/* 169:    */     }
/* 170:196 */     return this.listaTipoPresentacionProducto;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setListaTipoPresentacionProducto(List<TipoPresentacionProducto> listaTipoPresentacionProducto)
/* 174:    */   {
/* 175:200 */     this.listaTipoPresentacionProducto = listaTipoPresentacionProducto;
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.PresentacionProductoBean
 * JD-Core Version:    0.7.0.1
 */