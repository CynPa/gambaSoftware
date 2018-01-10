/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.VariableProcesoEnum;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="documento_variable_proceso")
/*  18:    */ public class DocumentoVariableProceso
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = -8825550776509395955L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="documento_variable_proceso", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento_variable_proceso")
/*  25:    */   @Column(name="id_documento_variable_proceso")
/*  26:    */   private int idDocumentoVariableProceso;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Enumerated(EnumType.ORDINAL)
/*  32:    */   @Column(name="documento_base", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private DocumentoBase documentoBase;
/*  35:    */   @Column(name="variable_proceso", nullable=false)
/*  36:    */   @Enumerated(EnumType.STRING)
/*  37:    */   private VariableProcesoEnum variableProceso;
/*  38:    */   
/*  39:    */   public int getIdDocumentoVariableProceso()
/*  40:    */   {
/*  41: 69 */     return this.idDocumentoVariableProceso;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIdDocumentoVariableProceso(int idDocumentoVariableProceso)
/*  45:    */   {
/*  46: 79 */     this.idDocumentoVariableProceso = idDocumentoVariableProceso;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public DocumentoBase getDocumentoBase()
/*  50:    */   {
/*  51: 88 */     return this.documentoBase;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/*  55:    */   {
/*  56: 98 */     this.documentoBase = documentoBase;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public VariableProcesoEnum getVariableProceso()
/*  60:    */   {
/*  61:107 */     return this.variableProceso;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setVariableProceso(VariableProcesoEnum variableProceso)
/*  65:    */   {
/*  66:117 */     this.variableProceso = variableProceso;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdOrganizacion()
/*  70:    */   {
/*  71:126 */     return this.idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdOrganizacion(int idOrganizacion)
/*  75:    */   {
/*  76:136 */     this.idOrganizacion = idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdSucursal()
/*  80:    */   {
/*  81:145 */     return this.idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdSucursal(int idSucursal)
/*  85:    */   {
/*  86:155 */     this.idSucursal = idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getId()
/*  90:    */   {
/*  91:165 */     return this.idDocumentoVariableProceso;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DocumentoVariableProceso
 * JD-Core Version:    0.7.0.1
 */