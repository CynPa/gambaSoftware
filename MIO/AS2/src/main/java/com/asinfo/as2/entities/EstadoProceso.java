/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="estado_proceso", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "documento_base", "estado"})})
/*  17:    */ public class EstadoProceso
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="estado_proceso", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="estado_proceso")
/*  24:    */   @Column(name="id_estado_proceso")
/*  25:    */   private int idEstadoProceso;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="documento_base", nullable=false)
/*  31:    */   @Enumerated(EnumType.ORDINAL)
/*  32:    */   private DocumentoBase documentoBase;
/*  33:    */   @Column(name="estado", nullable=false)
/*  34:    */   @Enumerated(EnumType.ORDINAL)
/*  35:    */   private Estado estado;
/*  36:    */   
/*  37:    */   public int getId()
/*  38:    */   {
/*  39: 78 */     return this.idEstadoProceso;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getidEstadoProceso()
/*  43:    */   {
/*  44: 86 */     return this.idEstadoProceso;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setidEstadoProceso(int idEstadoProceso)
/*  48:    */   {
/*  49: 94 */     this.idEstadoProceso = idEstadoProceso;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdOrganizacion()
/*  53:    */   {
/*  54:102 */     return this.idOrganizacion;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdOrganizacion(int idOrganizacion)
/*  58:    */   {
/*  59:110 */     this.idOrganizacion = idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdSucursal()
/*  63:    */   {
/*  64:118 */     return this.idSucursal;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdSucursal(int idSucursal)
/*  68:    */   {
/*  69:126 */     this.idSucursal = idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public DocumentoBase getDocumentoBase()
/*  73:    */   {
/*  74:133 */     return this.documentoBase;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/*  78:    */   {
/*  79:140 */     this.documentoBase = documentoBase;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Estado getEstado()
/*  83:    */   {
/*  84:147 */     return this.estado;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setEstado(Estado estado)
/*  88:    */   {
/*  89:154 */     this.estado = estado;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.EstadoProceso
 * JD-Core Version:    0.7.0.1
 */