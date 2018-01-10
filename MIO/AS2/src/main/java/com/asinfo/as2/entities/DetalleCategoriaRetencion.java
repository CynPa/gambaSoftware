/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="detalle_categoria_retencion")
/*  17:    */ public class DetalleCategoriaRetencion
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -4405415305165990875L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="detalle_categoria_retencion", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_categoria_retencion")
/*  24:    */   @Column(name="id_detalle_categoria_retencion", unique=true, nullable=false)
/*  25:    */   private int idDetalleCategoriaRetencion;
/*  26:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  27:    */   @JoinColumn(name="id_categoria_retencion", nullable=true)
/*  28:    */   private CategoriaRetencion categoriaRetencion;
/*  29:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  30:    */   @JoinColumn(name="id_concepto_retencionSRI", nullable=true)
/*  31:    */   private ConceptoRetencionSRI conceptoRetencionSRI;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   
/*  37:    */   public int getId()
/*  38:    */   {
/*  39: 72 */     return this.idDetalleCategoriaRetencion;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getIdDetalleCategoriaRetencion()
/*  43:    */   {
/*  44: 81 */     return this.idDetalleCategoriaRetencion;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setIdDetalleCategoriaRetencion(int idDetalleCategoriaRetencion)
/*  48:    */   {
/*  49: 91 */     this.idDetalleCategoriaRetencion = idDetalleCategoriaRetencion;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public CategoriaRetencion getCategoriaRetencion()
/*  53:    */   {
/*  54:100 */     return this.categoriaRetencion;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setCategoriaRetencion(CategoriaRetencion categoriaRetencion)
/*  58:    */   {
/*  59:110 */     this.categoriaRetencion = categoriaRetencion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public ConceptoRetencionSRI getConceptoRetencionSRI()
/*  63:    */   {
/*  64:119 */     return this.conceptoRetencionSRI;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setConceptoRetencionSRI(ConceptoRetencionSRI conceptoRetencionSRI)
/*  68:    */   {
/*  69:129 */     this.conceptoRetencionSRI = conceptoRetencionSRI;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdOrganizacion()
/*  73:    */   {
/*  74:137 */     return this.idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdOrganizacion(int idOrganizacion)
/*  78:    */   {
/*  79:145 */     this.idOrganizacion = idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdSucursal()
/*  83:    */   {
/*  84:153 */     return this.idSucursal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdSucursal(int idSucursal)
/*  88:    */   {
/*  89:161 */     this.idSucursal = idSucursal;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleCategoriaRetencion
 * JD-Core Version:    0.7.0.1
 */