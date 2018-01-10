/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="detalle_visualizacion")
/*  17:    */ public class DetalleVisualizacion
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="detalle_visualizacion", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_visualizacion")
/*  24:    */   @Column(name="id_detalle_visualizacion", unique=true, nullable=false)
/*  25:    */   private int idDetalleVisualizacion;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @NotNull
/*  29:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  30:    */   @JoinColumn(name="id_visualizacion", nullable=false)
/*  31:    */   private Visualizacion visualizacion;
/*  32:    */   @NotNull
/*  33:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  34:    */   @JoinColumn(name="id_categoria_producto", nullable=false)
/*  35:    */   private CategoriaProducto categoriaProducto;
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_subcategoria_producto", nullable=true)
/*  38:    */   private SubcategoriaProducto subcategoriaProducto;
/*  39:    */   
/*  40:    */   public int getId()
/*  41:    */   {
/*  42: 68 */     return this.idDetalleVisualizacion;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getIdDetalleVisualizacion()
/*  46:    */   {
/*  47: 72 */     return this.idDetalleVisualizacion;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setIdDetalleVisualizacion(int idDetalleVisualizacion)
/*  51:    */   {
/*  52: 76 */     this.idDetalleVisualizacion = idDetalleVisualizacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdOrganizacion()
/*  56:    */   {
/*  57: 80 */     return this.idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdOrganizacion(int idOrganizacion)
/*  61:    */   {
/*  62: 84 */     this.idOrganizacion = idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Visualizacion getVisualizacion()
/*  66:    */   {
/*  67: 88 */     return this.visualizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setVisualizacion(Visualizacion visualizacion)
/*  71:    */   {
/*  72: 92 */     this.visualizacion = visualizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public CategoriaProducto getCategoriaProducto()
/*  76:    */   {
/*  77: 96 */     return this.categoriaProducto;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/*  81:    */   {
/*  82:100 */     this.categoriaProducto = categoriaProducto;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public SubcategoriaProducto getSubcategoriaProducto()
/*  86:    */   {
/*  87:104 */     return this.subcategoriaProducto;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/*  91:    */   {
/*  92:108 */     this.subcategoriaProducto = subcategoriaProducto;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleVisualizacion
 * JD-Core Version:    0.7.0.1
 */