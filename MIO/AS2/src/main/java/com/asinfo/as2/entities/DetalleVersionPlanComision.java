/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.FormaPagoComisionEnum;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="detalle_version_plan_comision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_version_plan_comision", "id_categoria_producto", "id_subcategoria_producto", "id_producto"})})
/*  24:    */ public class DetalleVersionPlanComision
/*  25:    */   extends EntidadBase
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="detalle_version_plan_comision", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_version_plan_comision")
/*  31:    */   @Column(name="id_detalle_version_plan_comision")
/*  32:    */   private int idDetalleVersionPlanComision;
/*  33:    */   @Column(name="id_organizacion", nullable=false)
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal", nullable=false)
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="descripcion", nullable=true, length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="forma_pago_comision_enum", nullable=false)
/*  41:    */   @Enumerated(EnumType.ORDINAL)
/*  42:    */   @NotNull
/*  43:    */   private FormaPagoComisionEnum formaPagoComisionEnum;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_version_plan_comision", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private VersionPlanComision versionPlanComision;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_categoria_producto", nullable=true)
/*  50:    */   private CategoriaProducto categoriaProducto;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_subcategoria_producto", nullable=true)
/*  53:    */   private SubcategoriaProducto subcategoriaProducto;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_producto", nullable=true)
/*  56:    */   private Producto producto;
/*  57:    */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="detalleVersionPlanComision")
/*  58: 94 */   private List<DetalleVersionPlanComisionRangoDias> listaDetalleVersionPlanComisionRangoDias = new ArrayList();
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62:114 */     return this.idDetalleVersionPlanComision;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdDetalleVersionPlanComision()
/*  66:    */   {
/*  67:118 */     return this.idDetalleVersionPlanComision;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdDetalleVersionPlanComision(int idDetalleVersionPlanComision)
/*  71:    */   {
/*  72:122 */     this.idDetalleVersionPlanComision = idDetalleVersionPlanComision;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdOrganizacion()
/*  76:    */   {
/*  77:126 */     return this.idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:130 */     this.idOrganizacion = idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:134 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:138 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getDescripcion()
/*  96:    */   {
/*  97:142 */     return this.descripcion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setDescripcion(String descripcion)
/* 101:    */   {
/* 102:146 */     this.descripcion = descripcion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public FormaPagoComisionEnum getFormaPagoComisionEnum()
/* 106:    */   {
/* 107:150 */     return this.formaPagoComisionEnum;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setFormaPagoComisionEnum(FormaPagoComisionEnum formaPagoComisionEnum)
/* 111:    */   {
/* 112:154 */     this.formaPagoComisionEnum = formaPagoComisionEnum;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public VersionPlanComision getVersionPlanComision()
/* 116:    */   {
/* 117:158 */     return this.versionPlanComision;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setVersionPlanComision(VersionPlanComision versionPlanComision)
/* 121:    */   {
/* 122:162 */     this.versionPlanComision = versionPlanComision;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public CategoriaProducto getCategoriaProducto()
/* 126:    */   {
/* 127:166 */     return this.categoriaProducto;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 131:    */   {
/* 132:170 */     this.categoriaProducto = categoriaProducto;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 136:    */   {
/* 137:174 */     return this.subcategoriaProducto;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 141:    */   {
/* 142:178 */     this.subcategoriaProducto = subcategoriaProducto;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Producto getProducto()
/* 146:    */   {
/* 147:182 */     return this.producto;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setProducto(Producto producto)
/* 151:    */   {
/* 152:186 */     this.producto = producto;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<DetalleVersionPlanComisionRangoDias> getListaDetalleVersionPlanComisionRangoDias()
/* 156:    */   {
/* 157:190 */     return this.listaDetalleVersionPlanComisionRangoDias;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaDetalleVersionPlanComisionRangoDias(List<DetalleVersionPlanComisionRangoDias> listaDetalleVersionPlanComisionRangoDias)
/* 161:    */   {
/* 162:194 */     this.listaDetalleVersionPlanComisionRangoDias = listaDetalleVersionPlanComisionRangoDias;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleVersionPlanComision
 * JD-Core Version:    0.7.0.1
 */