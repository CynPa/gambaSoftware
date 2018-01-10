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
/*  11:    */ import javax.persistence.NamedQueries;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="serie_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_producto", "codigo"})})
/*  19:    */ @NamedQueries({@javax.persistence.NamedQuery(name="SerieProducto.buscarPorId", query="SELECT sp FROM SerieProducto sp JOIN FETCH sp.bodega JOIN FETCH sp.producto WHERE sp.idSerieProducto = :idSerieProducto"), @javax.persistence.NamedQuery(name="SerieProducto.buscarPorCodigo", query="SELECT sp FROM SerieProducto sp JOIN FETCH sp.bodega WHERE sp.idOrganizacion=:idOrganizacion AND sp.producto = :producto AND sp.codigo = :codigo")})
/*  20:    */ public class SerieProducto
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="serie_producto", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="serie_producto")
/*  27:    */   @Column(name="id_serie_producto")
/*  28:    */   private int idSerieProducto;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @NotNull
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_producto", nullable=false)
/*  34:    */   private Producto producto;
/*  35:    */   @NotNull
/*  36:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  37:    */   @JoinColumn(name="id_bodega", nullable=false)
/*  38:    */   private Bodega bodega;
/*  39:    */   @NotNull
/*  40:    */   @Size(min=1, max=20)
/*  41:    */   @Column(name="codigo", length=20)
/*  42:    */   private String codigo;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44: 63 */   private boolean activo = true;
/*  45:    */   
/*  46:    */   public int getId()
/*  47:    */   {
/*  48: 68 */     return this.idSerieProducto;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public SerieProducto() {}
/*  52:    */   
/*  53:    */   public SerieProducto(Producto producto, String codido)
/*  54:    */   {
/*  55: 77 */     this.producto = producto;
/*  56: 78 */     this.codigo = codido;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdSerieProducto()
/*  60:    */   {
/*  61: 82 */     return this.idSerieProducto;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdSerieProducto(int idSerieProducto)
/*  65:    */   {
/*  66: 86 */     this.idSerieProducto = idSerieProducto;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdOrganizacion()
/*  70:    */   {
/*  71: 90 */     return this.idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdOrganizacion(int idOrganizacion)
/*  75:    */   {
/*  76: 94 */     this.idOrganizacion = idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getCodigo()
/*  80:    */   {
/*  81: 98 */     return this.codigo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCodigo(String codigo)
/*  85:    */   {
/*  86:102 */     this.codigo = codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isActivo()
/*  90:    */   {
/*  91:106 */     return this.activo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setActivo(boolean activo)
/*  95:    */   {
/*  96:110 */     this.activo = activo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Producto getProducto()
/* 100:    */   {
/* 101:114 */     return this.producto;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setProducto(Producto producto)
/* 105:    */   {
/* 106:118 */     this.producto = producto;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Bodega getBodega()
/* 110:    */   {
/* 111:122 */     return this.bodega;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setBodega(Bodega bodega)
/* 115:    */   {
/* 116:126 */     this.bodega = bodega;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.SerieProducto
 * JD-Core Version:    0.7.0.1
 */