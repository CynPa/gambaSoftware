/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageController;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.SessionScoped;
/*  17:    */ import javax.faces.model.SelectItem;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.context.RequestContext;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @SessionScoped
/*  25:    */ public class ListaCuentaContableBean
/*  26:    */   extends PageController
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 6583450793826204104L;
/*  29:    */   @EJB
/*  30:    */   private ServicioCuentaContable servicioCuentaContable;
/*  31:    */   private CuentaContable cuentaContable;
/*  32:    */   private LazyDataModel<CuentaContable> listaCuentaContable;
/*  33: 57 */   private String prefijoBusquedaCuenta = null;
/*  34: 58 */   private boolean indicadorSeleccionarTodo = false;
/*  35:    */   private boolean manejaCuentaAlterna;
/*  36:    */   private SelectItem[] listaActivoItem;
/*  37:    */   private List<String[]> filtros;
/*  38:    */   private DataTable dtCuentaContable;
/*  39:    */   
/*  40:    */   @PostConstruct
/*  41:    */   public void init()
/*  42:    */   {
/*  43: 73 */     this.listaCuentaContable = new LazyDataModel()
/*  44:    */     {
/*  45:    */       private static final long serialVersionUID = 1L;
/*  46:    */       
/*  47:    */       public List<CuentaContable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  48:    */       {
/*  49: 79 */         List<CuentaContable> lista = new ArrayList();
/*  50:    */         
/*  51: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  52: 83 */         if (!filters.containsKey("activo")) {
/*  53: 85 */           filters.put("activo", String.valueOf(true));
/*  54:    */         }
/*  55: 88 */         if (ListaCuentaContableBean.this.prefijoBusquedaCuenta != null)
/*  56:    */         {
/*  57: 89 */           String codigo = (String)filters.get("codigo");
/*  58: 94 */           if ((codigo == null) || (!codigo.startsWith(ListaCuentaContableBean.this.prefijoBusquedaCuenta))) {
/*  59: 95 */             filters.put("codigo", ListaCuentaContableBean.this.prefijoBusquedaCuenta);
/*  60:    */           }
/*  61:    */         }
/*  62:101 */         String filtroCodigo = (String)filters.get("codigo");
/*  63:102 */         if (filtroCodigo != null) {
/*  64:103 */           filters.put("codigo", filtroCodigo + "%");
/*  65:    */         }
/*  66:111 */         lista = ListaCuentaContableBean.this.servicioCuentaContable.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  67:112 */         ListaCuentaContableBean.this.listaCuentaContable.setRowCount(ListaCuentaContableBean.this.servicioCuentaContable.contarPorCriterio(filters));
/*  68:    */         
/*  69:114 */         return lista;
/*  70:    */       }
/*  71:    */     };
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void agregarFiltro(String[] filtro)
/*  75:    */   {
/*  76:120 */     if (this.filtros == null) {
/*  77:121 */       this.filtros = new ArrayList();
/*  78:    */     }
/*  79:122 */     if (filtro.length > 0) {
/*  80:123 */       this.filtros.add(filtro);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void seleccionarCuentaContable(CuentaContable cuentaContable)
/*  85:    */   {
/*  86:128 */     RequestContext.getCurrentInstance().closeDialog(cuentaContable);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void listarCuentaContable()
/*  90:    */   {
/*  91:133 */     Map<String, Object> properties = new HashMap();
/*  92:134 */     properties.put("modal", Boolean.valueOf(true));
/*  93:135 */     properties.put("resizable", Boolean.valueOf(false));
/*  94:136 */     properties.put("draggable", Boolean.valueOf(true));
/*  95:137 */     properties.put("width", Integer.valueOf(680));
/*  96:138 */     properties.put("height", Integer.valueOf(360));
/*  97:139 */     RequestContext.getCurrentInstance().openDialog("/resources/componentes/seleccionarCuentaContable", properties, null);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public CuentaContable getCuentaContable()
/* 101:    */   {
/* 102:148 */     return this.cuentaContable;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 106:    */   {
/* 107:158 */     this.cuentaContable = cuentaContable;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public DataTable getDtCuentaContable()
/* 111:    */   {
/* 112:167 */     return this.dtCuentaContable;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 116:    */   {
/* 117:177 */     this.dtCuentaContable = dtCuentaContable;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public LazyDataModel<CuentaContable> getListaCuentaContable()
/* 121:    */   {
/* 122:186 */     return this.listaCuentaContable;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setListaCuentaContable(LazyDataModel<CuentaContable> listaCuentaContable)
/* 126:    */   {
/* 127:197 */     this.listaCuentaContable = listaCuentaContable;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getPrefijoBusquedaCuenta()
/* 131:    */   {
/* 132:204 */     return this.prefijoBusquedaCuenta;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setPrefijoBusquedaCuenta(String prefijoBusquedaCuenta)
/* 136:    */   {
/* 137:212 */     this.prefijoBusquedaCuenta = prefijoBusquedaCuenta;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isIndicadorSeleccionarTodo()
/* 141:    */   {
/* 142:216 */     return this.indicadorSeleccionarTodo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setIndicadorSeleccionarTodo(boolean indicadorSeleccionarTodo)
/* 146:    */   {
/* 147:220 */     this.indicadorSeleccionarTodo = indicadorSeleccionarTodo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isManejaCuentaAlterna()
/* 151:    */   {
/* 152:224 */     return ParametrosSistema.isCuentaAlterna(AppUtil.getOrganizacion().getId()).booleanValue();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public SelectItem[] getListaActivoItem()
/* 156:    */   {
/* 157:228 */     if (this.listaActivoItem == null)
/* 158:    */     {
/* 159:229 */       List<SelectItem> lista = new ArrayList();
/* 160:230 */       lista.add(new SelectItem("", ""));
/* 161:231 */       lista.add(new SelectItem("false", "false"));
/* 162:232 */       lista.add(new SelectItem("true", "true"));
/* 163:233 */       this.listaActivoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 164:    */     }
/* 165:235 */     return this.listaActivoItem;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean
 * JD-Core Version:    0.7.0.1
 */